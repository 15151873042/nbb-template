package com.nbb.template.system.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.nbb.template.system.service.SysPermissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限认证接口，实现此接口即可集成权限认证功能
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SysPermissionService sysPermissionService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 每次访问带有权限标识的接口都会回调该方法
        return CollUtil.newArrayList(sysPermissionService.listMenuPermission(Long.valueOf((String) loginId)));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return CollUtil.newArrayList(sysPermissionService.listRolePermission(Long.valueOf((String) loginId)));
    }
}
