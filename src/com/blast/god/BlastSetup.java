package com.blast.god;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class BlastSetup implements Setup {

    private final static Log log = Logs.get();

    @Override
    public void init(NutConfig nc) {
        log.infof("Blast version %s", Blast.VERSION);
    }

    @Override
    public void destroy(NutConfig nc) {}

}
