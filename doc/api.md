Blast API
===========

## 读

*HTTP GET 参数:*

    lon         # 经纬度
    lat         # 纬度
    me          # 当前用户名
    ld          # 最后一个收到的 blast 的ID
    n           # 最多要多少条数据

*HTTP 返回*

    {
        ok : true,
        msg : null,
        data : [
            { ... // Blast 的 JSON 字符串 ...},
            ...
        ]
    }

## 发布

*HTTP POST 参数*

    ----------------------------------------------------
    HEADER 
    Content-Type : application/json
    ----------------------------------------------------
    {
        ... // blast 的 JSON 字符串
    }

*HTTP 返回*

    {

    }

## 图，写

## 图，读

## 转发

## 趋势图表