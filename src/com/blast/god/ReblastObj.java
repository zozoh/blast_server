package com.blast.god;

import org.bson.types.ObjectId;
import org.nutz.json.JsonField;
import org.nutz.mongo.annotation.MoField;

public class ReblastObj {

    @JsonField(forceString = true)
    private ObjectId _id;

    @MoField("ow")
    private String owner;

    @JsonField(forceString = true)
    @MoField("bid")
    private ObjectId blastId;

    @MoField("lo")
    private double[] location;

    @MoField("ctm")
    private long createTime;

    @MoField("lm")
    private long lastModified;

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

    public ObjectId getBlastId() {
        return blastId;
    }

    public void setBlastId(ObjectId blastId) {
        this.blastId = blastId;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
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
