package com.nbb.template.system.service;

import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysPermissionService {


    Set<String> listRolePermission(Long userId);

    Set<String> listMenuPermission(Long userId);


}
