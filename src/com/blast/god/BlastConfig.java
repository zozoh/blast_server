package com.blast.god;

import java.io.File;

import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.mongo.ZMoCo;
import org.nutz.mongo.ZMoDB;
import org.nutz.mongo.ZMongo;
import org.nutz.web.WebConfig;

public class BlastConfig extends WebConfig {

    public BlastConfig(String path) {
        super(path);
    }

    private File home;

    public File getHome() {
        if (null == home) {
            synchronized (this) {
                if (null == home) {
                    home = Files.createDirIfNoExists(get("blast-god-home"));
                }
            }
        }
        return home;
    }

    private ZMoCo _co_blast;

    public ZMoCo getBlastCollection() {
        if (null == _co_blast) {
            synchronized (this) {
                if (null == _co_blast) {
                    _co_blast = _check_collection("mongo-blast");
                }
            }
        }
        return _co_blast;
    }

    private ZMoCo _co_rebla;

    public ZMoCo getReblastCollection() {
        if (null == _co_rebla) {
            synchronized (this) {
                if (null == _co_rebla) {
                    _co_rebla = _check_collection("mongo-rebla");
                }
            }
        }
        return _co_rebla;
    }

    private ZMoCo _check_collection(String coKey) {
        ZMoDB zdb = _check_zdb();
        // 如果没有集合，创建
        String co = this.check(coKey);
        ZMoCo mco;
        if (!zdb.cExists(co)) {
            mco = zdb.createCollection(co, null);
            // TODO 这里加入索引的设置 ...
        } else {
            mco = zdb.c(co);
        }
        return mco;
    }

    private ZMoDB _zdb;

    private ZMoDB _check_zdb() {
        if (null == _zdb) {
            // 获取配置信息
            String host = this.check("mongo-host");
            int port = this.getInt("mongo-port", -1);
            String usr = this.get("mongo-usr");
            String pwd = this.get("mongo-pwd");
            String db = this.check("mongo-db");

            // 连接数据库
            ZMongo zm;
            if (port <= 0) {
                if (Strings.isBlank(usr)) {
                    zm = ZMongo.me(host);
                } else {
                    zm = ZMongo.me(usr, pwd, host);
                }
            } else {
                if (Strings.isBlank(usr)) {
                    zm = ZMongo.me(host, port);
                } else {
                    zm = ZMongo.me(usr, pwd, host, port);
                }

            }
            _zdb = zm.db(db);
        }
        return _zdb;
    }

}
