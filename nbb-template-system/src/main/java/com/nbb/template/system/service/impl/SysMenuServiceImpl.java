package com.nbb.template.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.mapper.SysMenuMapper;
import com.nbb.template.system.mapper.SysRoleMenuMapper;
import com.nbb.template.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
