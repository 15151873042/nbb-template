package com.nbb.template.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.utils.SecurityUtils;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.mapper.SysMenuMapper;
import com.nbb.template.system.mapper.SysRoleMenuMapper;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysUserService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuDO> implements SysMenuService {


    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysUserService sysUserService;

    @Override
    public Set<String> listPermsByRoleId(Long roleId) {
        MPJLambdaWrapper<SysRoleMenuDO> wrapper = new MPJLambdaWrapper<SysRoleMenuDO>()
                .innerJoin(SysMenuDO.class, SysMenuDO::getId, SysRoleMenuDO::getMenuId)
                .select(SysMenuDO::getId, SysMenuDO::getPerms)
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .distinct();

        List<SysMenuDO> menuList = sysRoleMenuMapper.selectJoinList(SysMenuDO.class, wrapper);
        return menuList.stream()
                .map(SysMenuDO::getPerms)
                .filter(StrUtil::isNotEmpty)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(cacheNames = CoreCacheConstants.ROLE_MENU_KEY, key = "#roleId", unless = "#result == null")
    public List<SysMenuDO> listMenuByRoleId(Long roleId) {
        MPJLambdaWrapper<SysRoleMenuDO> wrapper = new MPJLambdaWrapper<SysRoleMenuDO>()
                .innerJoin(SysMenuDO.class, SysMenuDO::getId, SysRoleMenuDO::getMenuId)
                .selectAll(SysMenuDO.class)
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .eq(SysMenuDO::getStatus, "0")
                .in(SysMenuDO::getMenuType, "M", "C")
                .distinct();

        return sysRoleMenuMapper.selectJoinList(SysMenuDO.class, wrapper);
    }

    @Override
    public List<SysMenuDO> getMenuTreeByUserId(long userId) {
        List<SysMenuDO> menus;

        if (SecurityUtils.isAdmin(userId)) {
            menus = getBaseMapper().listMenuAll();
        } else {

            Set<Long> roleIds = sysUserService.listRoleIdById(userId);

            menus = roleIds.stream()
                    .flatMap(roleId -> ((SysMenuService) AopContext.currentProxy()).listMenuByRoleId(roleId).stream())
                    .distinct()
                    .collect(Collectors.toList());
        }

        return menus;
    }
}
