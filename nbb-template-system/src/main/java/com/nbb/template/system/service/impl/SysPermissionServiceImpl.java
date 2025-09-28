package com.nbb.template.system.service.impl;

import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysPermissionService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;


    @Override
    public Set<String> listRolePermission(Long userId) {
        if (this.isUserAdmin(userId)) {
            return Collections.singleton("admin");
        }

        return sysUserService.listRoleKeyById(userId);
    }

    @Override
    public Set<String> listMenuPermission(Long userId) {
        if (this.isUserAdmin(userId)) {
            return Collections.singleton("*:*:*");
        }

        Set<Long> roleIds = sysUserService.listRoleIdById(userId);

        return roleIds.parallelStream()
                .flatMap(roleId -> sysMenuService.listPermsByRoleId(roleId).stream())
                .collect(Collectors.toSet());
    }


    private boolean isUserAdmin(Long userId) {
        return userId != null && 1L == userId;
    }



}
