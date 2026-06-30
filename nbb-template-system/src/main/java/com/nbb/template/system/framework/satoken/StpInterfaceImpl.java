package com.nbb.template.system.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import com.nbb.template.system.core.utils.ServiceExceptionUtil;
import com.nbb.template.system.service.SysSessionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 权限认证接口，实现此接口即可集成权限认证功能
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SysSessionService sysSessionService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        if (!loginId.equals(StpUtil.getLoginId())) {
            throw ServiceExceptionUtil.exception("只能获取当前登录用户的权限码集合");
        }

        Set<String> menuPerms = sysSessionService.listMenuPermission();
        return ListUtil.toList(menuPerms);

    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        if (!loginId.equals(StpUtil.getLoginId())) {
            throw ServiceExceptionUtil.exception("只能获取当前登录用户的角色码集合");
        }

        Set<String> rolePerms = sysSessionService.listRolePermission();
        return ListUtil.toList(rolePerms);
    }
}
