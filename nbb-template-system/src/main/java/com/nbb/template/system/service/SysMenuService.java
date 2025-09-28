package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.domain.entity.SysMenuDO;

import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysMenuService extends IService<SysMenuDO> {


    /**
     *  获取所有角色的权限标识
     * @param roleId 角色id
     * @return
     */
    Set<String> listPermsByRoleId(Long roleId);

}
