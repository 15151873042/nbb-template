package com.nbb.template.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.nbb.template.system.core.utils.SecurityUtils;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysPermissionService;
import com.nbb.template.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

/**
 * @author 胡鹏
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleService sysRoleService;


    @Override
    public Set<String> listRolePermission(Long userId) {
        if (SecurityUtils.isAdmin(userId)) {
            return Collections.singleton("admin");
        }

        return sysRoleService.listRolePermissionByUserId(userId);
    }

    @Override
    public Set<String> listMenuPermission(Long userId) {
        if (SecurityUtils.isAdmin(userId)) {
            return Collections.singleton("*:*:*");
        }

        return sysMenuService.listMenuPermsByUserId(StpUtil.getLoginIdAsLong());
    }

}
