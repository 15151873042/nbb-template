package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RolePageDTO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.framework.mybatis.BaseMapperX;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface SysRoleMapper extends BaseMapperX<SysRoleDO> {

    default PageResult<SysRoleDO> listPageRole(RolePageDTO dto) {
        LambdaQueryWrapper<SysRoleDO> queryWrapper = new LambdaQueryWrapperX<SysRoleDO>()
                .likeIfPresent(SysRoleDO::getRoleName, dto.getRoleName())
                .likeIfPresent(SysRoleDO::getRoleKey, dto.getRoleKey())
                .eqIfPresent(SysRoleDO::getStatus, dto.getStatus())
                .geIfPresent(SysRoleDO::getCreateTime, dto.getBeginTime())
                .leIfPresent(SysRoleDO::getCreateTime, dto.getEndTime())
                .orderByDesc(SysRoleDO::getCreateTime, SysRoleDO::getId);

        return selectPage(dto, queryWrapper);
    }

    /**
     * 根据角色id批量删除角色信息
     * @param roleIds 角色id列表
     */
    default void deleteByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleDO> deleteWrapper = new LambdaQueryWrapperX<SysRoleDO>()
                .in(SysRoleDO::getId, roleIds);

        delete(deleteWrapper);
    }
}
