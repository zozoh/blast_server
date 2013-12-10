package com.blast.god;

import org.bson.types.ObjectId;
import org.nutz.json.JsonField;
import org.nutz.mongo.annotation.MoEnum;
import org.nutz.mongo.annotation.MoField;

public class BlastObj {

    @JsonField(forceString = true)
    private ObjectId _id;

    @MoField("ow")
    private String owner;

    @MoField("lo")
    private double[] location;

    @MoField("rnb")
    private int reblaNumber;

    @MoField("pic")
    private String picture;

    @MoField("cnt")
    private String content;

    @MoField("lv")
    @MoEnum(str = true)
    private int live;

    @MoField("ctm")
    private long createTime;

    @MoField("lm")
    private long lastModified;

    private String showDate;

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

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

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] position) {
        this.location = position;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

}
