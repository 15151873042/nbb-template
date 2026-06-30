package com.nbb.template.system.service;

import com.nbb.template.system.domain.entity.SysUserDO;

import java.util.List;
import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysSessionService {


    /**
     * 刷新用户session，将用户最新角色，权限标识放入session
     */
    void refreshUserSession();

    Set<String> listRolePermission();

    Set<String> listMenuPermission();

    SysUserDO getUserInfo();


}
