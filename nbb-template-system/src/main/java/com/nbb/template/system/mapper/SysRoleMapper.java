package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RolePageDTO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.framework.mybatis.mapper.BaseMapperX;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;

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
                .orderByAsc(SysRoleDO::getRoleSort)
                .orderByDesc(SysRoleDO::getCreateTime, SysRoleDO::getId);

        return selectPage(dto, queryWrapper);
    }
}
