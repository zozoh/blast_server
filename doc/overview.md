Blast 概述
======

### 特性列表

![](UI-brief.png)

### Blast的对象

    {
        _id           : "78..90",
        owner         : "zozoh",          # :ow  : 发起人的ID
        location      : [18.45619,-23.45833], # :lo: 位置信息[经度，纬度]
        reblaNumber   : 897,              # :rnb : 有多少消息引用它
        picture       : "79..45",         # :pic : Blast 的图片
        content       : "Hello",          # :cnt : 文字内容
        live          : 300,              # :lv  : 生存的时间，单位秒
        createTime    : new Date(),       # :created_at : Mongo 维护
        lastModified  : new Date(),       # :updated_at  : Mongo 维护
        picurl : "http://blast.com/pic/raw/96/79...45.jpg"
    }

* *_id* 不用上传
* *url* 不用上传
* *reblaNumber* 不用上传
* *live* 不用上传
* *createTime* 不用上传
* *lastModifiedm* 不用上传


### Reblast 对象

    {
        _id           : "e9..4a",
        blastId       : "78..90",           # :bid : 被 Reblast 的 ID
        owner         : "zozoh",            # :ow  : Reblast 人的ID
        location      : [18.45619,-23.458], # :lo : 位置信息[经度，纬度]
        createTime    : new Date(),         # :created_at : Mongo 维护
        lastModified  : new Date()          # :updated_at  : Mongo 维护
    }