package com.nbb.template.system.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.service.SysPermissionService;
import com.nbb.template.system.service.SysSessionService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author 胡鹏
 */
@Service
public class SysSessionServiceImpl implements SysSessionService {


    @Resource
    private SysUserService userService;
    @Resource
    private SysPermissionService permissionService;


    @Override
    public void refreshUserSession() {
        long userId = StpUtil.getLoginIdAsLong();
        // 用户基本信息
        SysUserDO userBasicInfo = userService.getUserBasicInfo(userId);
        // 角色集合
        Set<String> roles = permissionService.listRolePermission(userId);
        // 权限集合
        Set<String> permissions = permissionService.listMenuPermission(userId);
        // 用户信息放入用户session
        SaSession session = StpUtil.getSession();
        session.set(SaSession.USER, userBasicInfo);
        session.set(SaSession.ROLE_LIST, roles);
        session.set(SaSession.PERMISSION_LIST, permissions);
    }

    @Override
    public Set<String> listRolePermission() {
        return (Set<String>)StpUtil.getSession().get(SaSession.ROLE_LIST);
    }

    @Override
    public Set<String> listMenuPermission() {
        return (Set<String>)StpUtil.getSession().get(SaSession.PERMISSION_LIST);
    }

    @Override
    public SysUserDO getUserInfo() {
        return (SysUserDO)StpUtil.getSession().get(SaSession.USER);
    }
}
