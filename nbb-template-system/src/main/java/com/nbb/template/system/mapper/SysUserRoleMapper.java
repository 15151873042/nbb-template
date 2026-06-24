package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.domain.entity.SysUserRoleDO;
import com.nbb.template.system.framework.mybatis.BaseMapperX;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;

/**
 * @author 胡鹏
 */
public interface SysUserRoleMapper extends BaseMapperX<SysUserRoleDO> {

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
}
