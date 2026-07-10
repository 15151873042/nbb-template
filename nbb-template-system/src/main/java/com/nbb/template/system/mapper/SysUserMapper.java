package com.nbb.template.system.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RolePageDTO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.qo.UserPageQO;
import com.nbb.template.system.framework.mybatis.mapper.BaseMapperX;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;

/**
 * @author 胡鹏
 */
public interface SysUserMapper extends BaseMapperX<SysUserDO> {

    default PageResult<SysUserDO> listPageUser(UserPageQO qo) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapperX<SysUserDO>()
                .likeIfPresent(SysUserDO::getUserName, qo.getUserName())
                .likeIfPresent(SysUserDO::getPhonenumber, qo.getPhonenumber())
                .eqIfPresent(SysUserDO::getStatus, qo.getStatus())
                .geIfPresent(SysUserDO::getCreateTime, qo.getBeginTime())
                .leIfPresent(SysUserDO::getCreateTime, qo.getEndTime())
                .orderByDesc(SysUserDO::getCreateTime, SysUserDO::getId).
                and(qo.getDeptId() != null || CollUtil.isNotEmpty(qo.getDeptIds()),
                        w -> w.eq(qo.getDeptId() != null, SysUserDO::getDeptId, qo.getDeptId())
                                .or()
                                .in(CollUtil.isNotEmpty(qo.getDeptIds()), SysUserDO::getDeptId, qo.getDeptIds()));

        return selectPage(qo, queryWrapper);
    }
}
