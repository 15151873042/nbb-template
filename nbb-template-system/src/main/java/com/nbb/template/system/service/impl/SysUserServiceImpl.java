package com.nbb.template.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysUserMapper;
import com.nbb.template.system.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author 胡鹏
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {
    @Override
    public SysUserDO selectUserByUserName(String userName) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapperX<SysUserDO>()
                .eq(SysUserDO::getUserName, userName);
        return this.getOne(queryWrapper);
    }

    @Override
    public SysUserDO getUserBasicInfo(Long id) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapperX<SysUserDO>()
                .eq(SysUserDO::getId, id)
                .select(SysUserDO::getId, SysUserDO::getUserName, SysUserDO::getNickName, SysUserDO::getAvatar);
        return this.getOne(queryWrapper);
    }
}
