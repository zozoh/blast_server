# blast_server

 * 在 eclipse 建立一个普通的 Java 工程即可
 * 把 `src` 和 `conf` 目录加为源码目录
 * `conf` 里面的 `web.properties` 是程序的关键，
    里面你需要修改的就是一些路径
    * `app-root` 指向你硬盘的 `ROOT` 目录
    * `mongo-xxx` 看看意思应该就知道怎么配
    * `blast-god-home` 是数据文件的主目录
        * 下面的诸如 `blast-mock-path-xxxx` 都是基于这个目录的
 * 你的工程需要依赖如下 jar 包, 这些 jar 我都放到 `jar` 目录下
     * nutz.jar (*MVC 框架*)
     * nutz-web.jar (*针对 WEB 程序的扩展*)
     * nutz-mongo.jar (*对于 MongoDB 的封装*)
     * mongo-java-driver-2.9.3.jar (*MongoDB 的驱动*)
     * log4j-1.2.15.jar (*日志输出*)
     * jetty 相关的 jar 都在 `jetty` 目录，它是个可被嵌入的 Java Web 服务器
     
# 启动后的访问

 * API 部分，参见 [API文档](https://github.com/zozoh/blast_server/blob/master/doc/api.md)
 * 模拟数据部分功能参见类 "com.blast.god.module.BlastMockModule"
     * 访问 `http://localhost:8080/mock/god` 就是模拟数据页面
 * 程序的数据模型参看:
     * `com.blast.god.BlastObj` - Blast 数据表的映射
     * `com.blast.god.ReblastObj` - Reblast 数据表的映射
