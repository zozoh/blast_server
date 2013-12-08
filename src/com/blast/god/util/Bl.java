package com.blast.god.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;

/**
 * 会话服务的静态帮助函数
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class Bl {

    /**
     * @param str
     *            格式为 38C02'32.62,38C02'32.62
     * @return 一个两个元素的经纬度数组
     */
    public static double[] str2ll(String str) {
        String[] ss = Strings.splitIgnoreBlank(str);
        double[] re = new double[2];
        re[0] = str_d_to_float(ss[0]);
        re[1] = str_d_to_float(ss[1]);
        return re;
    }

    public static double str_d_to_float(String str) {
        String[] ss = Strings.splitIgnoreBlank(str, "[C’']");
        double v = Double.valueOf(ss[0]);
        v += Double.valueOf(ss[1]) / 60.0;
        v += Double.valueOf(ss[2]) / 3600.0;
        return v;
    }

    /**
     * 读取文件第 n 行并返回
     * 
     * @param f
     * @param n
     *            0 base
     * @return 行内容，trim 过的
     */
    public static String read_n(File f, int n) {
        BufferedReader br = Streams.buffr(Streams.fileInr(f));

        String line = null;
        try {
            int i = 0;
            while (null != (line = br.readLine())) {
                if (i++ < n)
                    continue;
                break;
            }
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
        finally {
            Streams.safeClose(br);
        }

        return line;
    }

}
