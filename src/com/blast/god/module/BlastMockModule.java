package com.blast.god.module;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.nutz.http.Header;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mongo.ZMoDoc;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.blast.god.BlastObj;
import com.blast.god.BlastUser;
import com.blast.god.ReblastObj;
import com.blast.god.util.Bl;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@IocBean
@Ok("ajax")
@Fail("ajax")
@At("/mock")
public class BlastMockModule extends AbstractBlastModule {

    private static final Log log = Logs.get();

    @Inject("java:$conf.get('blast-api')")
    private String apiUrl;

    @Inject("java:$conf.get('blast-mock-path-content')")
    private String phContent;

    @Inject("java:$conf.get('blast-mock-path-position')")
    private String phPosition;

    @At("/god")
    @Ok("jsp:jsp.god")
    public String show_god() {
        return home.getAbsolutePath();
    }

    @At("/reblast")
    public List<ReblastObj> do_reblast(@Param("lon") double lon,
                                       @Param("lat") double lat,
                                       @Param("distance") double distance,
                                       @Param("n") int n,
                                       @Param("bid") String bid) {
        if (n <= 0)
            n = 5;
        List<ReblastObj> list = new ArrayList<ReblastObj>(n);

        for (int i = 0; i < n; i++) {
            // 得到一个要 blast 的对象
            BlastObj bo = null;
            if (!Strings.isBlank(bid)) {
                ZMoDoc doc = coB.findOne(ZMoDoc.ID(new ObjectId(bid)));
                if (null != doc)
                    bo = mo.fromDocToObj(doc, BlastObj.class);
            }
            if (null == bo) {
                long count = coB.count();
                DBCursor cu = coB.find();
                int skip = R.random(0, (int) count - 1);
                cu.skip(skip);
                DBObject o = cu.next();
                bo = mo.fromDocToObj(o, BlastObj.class);
            }

            // 得到一个随机用户
            BlastUser bu = users[R.random(0, users.length - 1)];

            // 得到两个随机数
            double s = (double) R.random(-10, 10);
            double x = distance + 1.0 / (s == 0.0 ? 1.0 : s);

            s = (double) R.random(-10, 10);
            double y = distance + 1.0 / (s == 0.0 ? 1.0 : s);

            // 发送请求
            String url = apiUrl + "/api/blasts/reblast";
            Request req = Request.create(url,
                                         METHOD.POST,
                                         String.format("{lon:%f,lat:%f,me:'%s',bid:'%s'}",
                                                       lon + x,
                                                       lat + y,
                                                       bu.getName(),
                                                       bo.get_id().toString()),
                                         null);
            Sender sender = Sender.create(req, 10000);
            Response resp = sender.send();
            String retxt = resp.getContent();

            NutMap map = Json.fromJson(NutMap.class, retxt);
            NutMap data = map.getAs("data", NutMap.class);
            List<Double> lo = data.getList("location", Double.class);
            double[] ll = new double[]{lo.get(0), lo.get(1)};

            // 计入结果
            ReblastObj ro = new ReblastObj();
            ro.set_id(new ObjectId(data.getString("_id")));
            ro.setLocation(ll);
            ro.setOwner(data.getString("owner"));
            ro.setBlastId(new ObjectId(data.getString("blastId")));
            ro.setCreateTime(data.getLong("createTime"));
            ro.setLastModified(data.getLong("lastModified"));
            list.add(ro);
        }

        // 返回
        return list;
    }

    @At("/blast")
    @Ok("raw")
    public String do_blast(@Param("nb") int nb) {
        // 读取对应的信息
        BlastObj bo = new BlastObj();
        try {
            bo.set_id(new ObjectId());
            bo.setContent(Bl.read_n(Files.getFile(home, phContent), nb));
            bo.setLocation(Bl.str2ll(Bl.read_n(Files.getFile(home, phPosition),
                                               nb)));
        }
        catch (Exception e) {
            return "wrong mock number : " + nb;
        }

        // 读取图片
        File fPic = Files.getFile(home, "data/" + (nb + 1) + ".jpg");

        // 发送请求获取图片 ID
        String url = apiUrl + "/api/blasts/photo_upload";
        Header header = Header.create("{'Content-Type' : 'image/jpeg'}");
        Request req = Request.create(url, METHOD.POST, "{}", header);
        InputStream ins = Streams.fileIn(fPic);
        req.setInputStream(ins);

        Sender sender = Sender.create(req, 10000);
        Response resp = sender.send();
        String retxt = resp.getContent();

        if (log.isInfoEnabled())
            log.info("image << " + retxt);

        NutMap map = Json.fromJson(NutMap.class, retxt);

        String picId = map.getAs("data", NutMap.class).getString("_id");

        // 设置一下 bo
        bo.setPicture(picId);

        // 提交一个 blast
        url = apiUrl + "/api/blasts/new";
        header = Header.create("{'Content-Type' : 'application/json'}");
        req = Request.create(url, METHOD.POST, "{}", header);
        req.setData(Json.toJson(bo));

        sender = Sender.create(req, 10000);
        resp = sender.send();
        retxt = resp.getContent();

        // 返回
        return retxt;
    }
}
