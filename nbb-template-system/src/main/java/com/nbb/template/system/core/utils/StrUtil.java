package com.nbb.template.system.core.utils;

/**
 * @author 胡鹏
 */
public class StrUtil extends cn.hutool.core.util.StrUtil {

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return startWithAny(link, "http://", "https://");
    }
}
