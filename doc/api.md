Blast API
===========

## 读 : /api/blasts

*HTTP GET 参数:*

    lon         # 经纬度
    lat         # 纬度
    me          # 当前用户名
    ld          # 最后一个收到的 blast 的ID， 
                # 如果为空或者没有，表示获取最新的 n 条
    n           # 最多要多少条数据

*HTTP 返回*

    HTTP 200
    {
        ok : true,
        msg : null,
        data : [
            { ... // Blast 的 JSON 字符串 ...},
            ...
        ]
    }

## 新发 Blast : /api/blasts/new

*HTTP POST 参数*

    ----------------------------------------------------
    HEADER 
    Content-Type : application/json
    ----------------------------------------------------
    BODY
    {
        ... // blast 的 JSON 字符串
    }

*HTTP 返回*

    HTTP 201
    {
        ... // blast 的 JSON 字符串 带新的 blast ID
    }

## 图，写 : /api/blasts/photo_upload

*HTTP POST 参数*

    ----------------------------------------------------
    HEADER 
    Content-Type : image/jpeg
    ----------------------------------------------------
    BODY
    00 0e 34 34 67 ...

*HTTP 返回*

    HTTP 201
    {
        _id : "89..ae",
        picurl : "http://blast.com/pic/raw/96/79...45.jpg"
    }

## 图，读 : $BLAST.picurl

*HTTP GET 参数*

    ...  老大，看给你的 url 哦

*HTTP 返回*

    HTTP 200
    ----------------------------------------------------
    HEADER 
    Content-Type : image/jpeg
    ----------------------------------------------------
    BODY
    00 0e 34 34 67 ...

## 转发 : /api/blasts/reblast

*HTTP GET 参数:*

    lon         # 经纬度
    lat         # 纬度
    me          # 当前用户名
    bid         # Blast ID

*HTTP 返回*

    HTTP 200
    {
        ok : true,
        msg : null,
        data : [
            { ... // Blast 的 JSON 字符串 ...},
            ...
        ]
    }

## 趋势图表 : /api/blasts/graph

*HTTP GET 参数:* 

    bid         # Blast ID

*HTTP 返回*

    HTTP 200
    {
        ok : true,
        msg : null,
        data : [
            { ... // Blast 的 JSON 字符串 ...},
            ...
        ]
    }


















