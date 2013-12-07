Blast 概述
======

### 特性列表

![](UI-brief.png)

### Blast的对象

    {
        _id           : "78..90",
        referId       : "3e..ac",         # 引用消息 ID
        referNumber   : 897,              # 有多少消息引用它
        picture       : "79..45", 
        content       : "Hello", 
        live          : 300,              # 生存的时间，单位秒
        createTime    : new Date(),       # Mongo 维护
        lastModifiedm : new Date()        # Mongo 维护
    }
