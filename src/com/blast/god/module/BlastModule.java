package com.blast.god.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mongo.ZMo;
import org.nutz.mongo.ZMoCo;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

@IocBean
@Ok("ajax")
@Fail("ajax")
public class BlastModule {

    @Inject("java:org.nutz.mongo.ZMo.me()")
    private ZMo mo;

    @Inject("java:$conf.blastCollection")
    private ZMoCo coB;

    @Inject("java:$conf.reblastCollection")
    private ZMoCo coR;

    @Inject("java:$conf.home")
    private File home;

    @At("/")
    @Ok("jsp:jsp.god")
    public String show_god() {
        return home.getAbsolutePath();
    }
}
