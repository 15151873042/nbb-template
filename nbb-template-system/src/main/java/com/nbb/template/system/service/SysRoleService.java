package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.domain.entity.SysUserDO;

import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysRoleService extends IService<SysRoleDO> {


    /**
     * 通过角色id查询对应的菜单id
     * @param id 角色id
     * @return
     */
    Set<Long> listMenuIdById(Long id);

    /**
     * 通过角色id查询对应的菜单id
     * @param roleIds 角色Id列表
     * @return
     */
    Set<Long> listMenuIdByIds(Set<Long> roleIds);
}
