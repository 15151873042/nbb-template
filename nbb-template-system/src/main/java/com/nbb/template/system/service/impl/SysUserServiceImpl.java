package com.nbb.template.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.entity.SysUserRoleDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysUserMapper;
import com.nbb.template.system.mapper.SysUserRoleMapper;
import com.nbb.template.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {


    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
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

    @Override
    public Set<String> listRoleKeyById(Long id) {
        MPJLambdaWrapper<SysUserRoleDO> wrapper = new MPJLambdaWrapper<SysUserRoleDO>()
                .innerJoin(SysRoleDO.class, SysRoleDO::getId, SysUserRoleDO::getRoleId)
                .select(SysRoleDO::getRoleKey)
                .eq(SysUserRoleDO::getUserId, id)
                .distinct();

        List<SysRoleDO> roleList = sysUserRoleMapper.selectJoinList(SysRoleDO.class, wrapper);
        return roleList.stream().map(SysRoleDO::getRoleKey).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> listRoleIdById(Long id) {
        MPJLambdaWrapper<SysUserRoleDO> wrapper = new MPJLambdaWrapper<SysUserRoleDO>()
                .innerJoin(SysRoleDO.class, SysRoleDO::getId, SysUserRoleDO::getRoleId)
                .select(SysRoleDO::getId)
                .eq(SysUserRoleDO::getUserId, id)
                .distinct();

        List<SysRoleDO> roleList = sysUserRoleMapper.selectJoinList(SysRoleDO.class, wrapper);
        return roleList.stream().map(SysRoleDO::getId).collect(Collectors.toSet());

    }
}
