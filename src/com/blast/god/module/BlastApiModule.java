package com.blast.god.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mongo.ZMoDoc;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.web.Webs;

import com.blast.god.BlastObj;
import com.blast.god.BlastUser;
import com.blast.god.ReblastObj;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@IocBean
@Ok("ajax")
@Fail("ajax")
@At("/api")
public class BlastApiModule extends AbstractBlastModule {

    private static final Log log = Logs.get();

    @At("/signup")
    public String signup(@Param("uid") String uid) {
        for (BlastUser u : users) {
            if (Strings.isBlank(u.getID())) {
                u.setID(uid);
                return u.getName();
            }
        }
        return "shit!";
    }

    @At("/avatar")
    @Ok("raw:image/jpeg")
    public InputStream avatar(@Param("uid") String uid) {
        for (BlastUser u : users) {
            if (Strings.equals(uid, u.getID())) {
                String ph = "avatars/user" + u.getNumber() + ".png";
                File fAvata = Files.getFile(home, ph);
                if (fAvata.exists()) {
                    return Streams.fileIn(fAvata);
                }
            }
        }
        return Streams.fileIn("no_pic.jpg");
    }

    @At("/decay")
    public int do_decay() {
        DBCursor cu = coB.find();
        long now = System.currentTimeMillis();
        int count = 0;
        while (cu.hasNext()) {
            DBObject o = cu.next();
            ZMoDoc m = ZMoDoc.NEWf("{$inc: {lv:-10}, $set:{lm:%d} }", now);
            ZMoDoc q = ZMoDoc.ID(o.get("_id"));
            coB.update(q, m);
            count++;
        }
        return count;
    }

    @At("/blasts")
    public List<BlastObj> query_blasts(@Param("lon") double lon,
                                       @Param("lat") double lat,
                                       @Param("me") String me,
                                       @Param("ld") String ld,
                                       @Param("n") int n) {

        // 在自己附近
        ZMoDoc q = ZMoDoc.NEWf("{lo : { $near : [%f,%f] }}", lon, lat);

        // 确定最后的更新时间
        ZMoDoc lastDoc = null;
        if (!Strings.isBlank(ld)) {
            lastDoc = coB.findOne(ZMoDoc.ID(new ObjectId(ld)));
        }
        if (null != lastDoc)
            q.putv("lm", ZMoDoc.NEW("$gt", lastDoc.getTime("lm")));

        // 必须是活着的
        q.putv("lv", ZMoDoc.NEW("$gt", 0));

        // 确定最大值
        if (n <= 0)
            n = 10;

        // 遍历
        List<BlastObj> list = new LinkedList<BlastObj>();
        DBCursor cu = coB.find(q);
        cu.sort(ZMoDoc.NEW("{lm:1,rnb:1,lv:1}"));
        cu.limit(n);
        while (cu.hasNext()) {
            DBObject o = cu.next();
            BlastObj bo = mo.fromDocToObj(o, BlastObj.class);
            list.add(bo);
        }

        return list;

    }

    @AdaptBy(type = JsonAdaptor.class)
    @At("/blasts/new")
    public BlastObj create_blast(BlastObj bo) {
        bo.setLive(360);
        bo.setReblaNumber(0);
        bo.setCreateTime(Times.now().getTime());
        bo.setLastModified(bo.getCreateTime());

        ZMoDoc doc = mo.toDoc(bo).genID();
        coB.save(doc);

        return bo;
    }

    @At("/blasts/reblast")
    public ReblastObj make_reblast(@Param("lon") double lon,
                                   @Param("lat") double lat,
                                   @Param("me") String me,
                                   @Param("bid") String bid) {
        ZMoDoc q = ZMoDoc.ID(new ObjectId(bid));
        ZMoDoc doc = coB.findOne(q);
        if (null == doc) {
            throw Webs.Err.create("blast.noexists", bid);
        }

        long now = System.currentTimeMillis();

        ReblastObj ro = new ReblastObj();
        ro.setLocation(new double[]{lon, lat});
        ro.setBlastId(doc.getId());
        ro.setOwner(me);
        ro.setCreateTime(now);
        ro.setLastModified(ro.getCreateTime());
        ZMoDoc roDoc = mo.toDoc(ro).genID();
        coR.save(roDoc);

        ZMoDoc m = ZMoDoc.NEWf("{$inc: {lv:10, rnb:1}, $set:{lm:%d} }", now);
        coB.update(q, m);

        ro.set_id(roDoc.getId());

        return ro;
    }

    @At("/photo/?")
    @Ok("raw:image/jpeg")
    public InputStream photo_show(String sha1) {
        String ph = sha12ph(sha1);
        File fImg = Files.findFile(home.getAbsolutePath() + "/" + ph);
        if (fImg == null) {
            fImg = Files.findFile("no_pic.jpg");
        }
        return Streams.fileIn(fImg);
    }

    @At("/blasts/photo_upload")
    public NutMap photo_upload(HttpServletRequest req) throws IOException {

        if (log.isInfoEnabled())
            log.info(" >> photo_upload");

        InputStream ins = req.getInputStream();

        // 生成一个临时文件
        File tmp = Files.createFileIfNoExists(home.getAbsolutePath()
                                              + "/tmp/"
                                              + R.UU16());
        // 写入临时文件
        Files.write(tmp, ins);

        // 计算 SHA1
        String sha1 = Lang.sha1(tmp);
        String ph = sha12ph(sha1);
        File dst1 = Files.createFileIfNoExists(home.getAbsolutePath()
                                               + "/"
                                               + ph);
        File dst = dst1;

        // 移动文件到相应的位置

        Files.move(tmp, dst);

        NutMap re = new NutMap();
        re.put("_id", sha1);
        re.put("picurl", "/api/photo/" + sha1);
        return re;
    }

    private String sha12ph(String sha1) {
        String ph = "photo/"
                    + sha1.substring(0, 2)
                    + "/"
                    + sha1.substring(2)
                    + ".jpg";
        return ph;
    }

}
