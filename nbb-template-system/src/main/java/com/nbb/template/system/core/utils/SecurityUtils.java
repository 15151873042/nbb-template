package com.nbb.template.system.core.utils;

/**
 * @author 胡鹏
 */
public class SecurityUtils {

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
