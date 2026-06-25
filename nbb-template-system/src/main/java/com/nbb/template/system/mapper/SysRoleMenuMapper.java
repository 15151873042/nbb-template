package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.framework.mybatis.mapper.BaseMapperX;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
public interface SysRoleMenuMapper extends BaseMapperX<SysRoleMenuDO>  {


    /**
     * 根据角色id查询所关联的所有菜单id
     * @param roleId 角色id
     * @return 菜单id
     */
    default List<Long> listMenuIdByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenuDO> queryWrapper = new LambdaQueryWrapper<SysRoleMenuDO>()
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .select(SysRoleMenuDO::getMenuId);

        return selectList(queryWrapper)
                .stream()
                .map(SysRoleMenuDO::getMenuId)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色id删除角色菜单关联关系
     * @param roleId 角色id
     */
    default void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenuDO> deleteWrapper = new LambdaQueryWrapperX<SysRoleMenuDO>()
                .eq(SysRoleMenuDO::getRoleId, roleId);

        delete(deleteWrapper);
    }

    /**
     * 根据角色id批量删除角色菜单关联关系
     * @param roleIds 角色id列表
     */
    default void deleteByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleMenuDO> deleteWrapper = new LambdaQueryWrapperX<SysRoleMenuDO>()
                .in(SysRoleMenuDO::getRoleId, roleIds);

        delete(deleteWrapper);
    }


}
