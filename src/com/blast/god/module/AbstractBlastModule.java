package com.blast.god.module;

import java.io.File;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mongo.ZMo;
import org.nutz.mongo.ZMoCo;

import com.blast.god.BlastUser;

public class AbstractBlastModule {

    @Inject("java:org.nutz.mongo.ZMo.me()")
    protected ZMo mo;

    @Inject("java:$conf.blastCollection")
    protected ZMoCo coB;

    @Inject("java:$conf.reblastCollection")
    protected ZMoCo coR;

    @Inject("java:$conf.home")
    protected File home;

    @Inject("java:$conf.users")
    protected BlastUser[] users;

}
