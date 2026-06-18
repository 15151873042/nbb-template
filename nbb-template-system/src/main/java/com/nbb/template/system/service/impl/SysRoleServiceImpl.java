package com.nbb.template.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.domain.entity.SysUserRoleDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysMenuMapper;
import com.nbb.template.system.mapper.SysRoleMapper;
import com.nbb.template.system.mapper.SysRoleMenuMapper;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysRoleService;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    SysRoleService sysRoleService;

    @Override
    @Cacheable(cacheNames = CoreCacheConstants.ROLE_MENU_ID_KEY, key = "#id", unless = "#result == null")
    public Set<Long> listMenuIdById(Long id) {
        LambdaQueryWrapper<SysRoleMenuDO> queryWrapper = new LambdaQueryWrapper<SysRoleMenuDO>()
                .eq(SysRoleMenuDO::getRoleId, id);

        return sysRoleMenuMapper.selectList(queryWrapper)
                .stream()
                .map(SysRoleMenuDO::getMenuId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> listMenuIdByIds(Set<Long> roleIds) {
        return roleIds.stream()
                .flatMap(roleId -> ((SysRoleService) AopContext.currentProxy()).listMenuIdById(roleId).stream())
                .collect(Collectors.toSet());
    }
}
