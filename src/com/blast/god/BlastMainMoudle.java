package com.blast.god;

import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.web.ajax.AjaxViewMaker;

@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class,
// 采用复合加载器的参数
args = {"*org.nutz.ioc.loader.json.JsonLoader",
        "ioc",
        "*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
        "com.blast.god.module"})
@SetupBy(BlastSetup.class)
@Localization("msg")
@Views(AjaxViewMaker.class)
@ChainBy(args = {"chain"})
public class BlastMainMoudle {}
