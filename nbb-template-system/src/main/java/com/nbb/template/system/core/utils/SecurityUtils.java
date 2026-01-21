package com.nbb.template.system.core.utils;

import cn.dev33.satoken.stp.StpUtil;

/**
 * @author 胡鹏
 */
public class SecurityUtils {

    public static boolean isCurrentUserAdmin() {
        long loginUserId = StpUtil.getLoginIdAsLong();
        return isAdmin(loginUserId);
    }

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
