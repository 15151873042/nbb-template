package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.domain.entity.SysUserRoleDO;
import com.nbb.template.system.framework.mybatis.mapper.BaseMapperX;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
public interface SysUserRoleMapper extends BaseMapperX<SysUserRoleDO> {


    /**
     * 查询角色所关联的所有用户id
     * @param roleId 角色id
     * @return 角色所关联的用户id
     */
    default List<Long> listUserIdByRoleId(Long roleId) {
        LambdaQueryWrapper<SysUserRoleDO> queryWrapper = new LambdaQueryWrapperX<SysUserRoleDO>()
                .eq(SysUserRoleDO::getRoleId, roleId);

        return selectList(queryWrapper).stream()
                .map(SysUserRoleDO::getUserId)
                .collect(Collectors.toList());
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    default int countUserByRoleId(Long roleId) {
        LambdaQueryWrapper<SysUserRoleDO> queryWrapper = new LambdaQueryWrapperX<SysUserRoleDO>()
                .eq(SysUserRoleDO::getRoleId, roleId);

        return selectCount(queryWrapper).intValue();
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    default void deleteAuthUser(Long roleId, List<Long> userIds) {
        LambdaQueryWrapper<SysUserRoleDO> deleteWrapper = new LambdaQueryWrapperX<SysUserRoleDO>()
                .eq(SysUserRoleDO::getRoleId, roleId)
                .in(SysUserRoleDO::getUserId, userIds);

        delete(deleteWrapper);
    }
}
