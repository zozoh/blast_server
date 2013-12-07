package com.blast.god;

import java.util.Date;

import org.bson.types.ObjectId;
import org.nutz.json.JsonField;
import org.nutz.mongo.annotation.MoField;

public class ReblastObj {

    @JsonField(ignore = true)
    private ObjectId _id;

    @MoField("ow")
    private String owner;

    @MoField("pos")
    private float[] position;

    @MoField("ctm")
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
