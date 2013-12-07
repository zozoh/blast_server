package com.blast.god;

import java.util.Date;

import org.bson.types.ObjectId;
import org.nutz.json.JsonField;
import org.nutz.mongo.annotation.MoEnum;
import org.nutz.mongo.annotation.MoField;

public class BlastObj {

    @JsonField(ignore = false)
    private ObjectId _id;

    @MoField("ow")
    private String owner;

    @MoField("pos")
    private float[] position;

    @MoField("rnb")
    private int reblaNumber;

    @JsonField(ignore = true)
    @MoField("pic")
    private String picture;

    @MoField("cnt")
    private String content;

    @MoField("lv")
    @MoEnum(str = true)
    private int live;

    @MoField("ctm")
    private Date createTime;

    @MoField("lm")
    private Date lastModified;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public int getReblaNumber() {
        return reblaNumber;
    }

    public void setReblaNumber(int reblaNumber) {
        this.reblaNumber = reblaNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

}
