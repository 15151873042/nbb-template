package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.core.utils.StrUtil;
import com.nbb.template.system.domain.dto.MenuListDTO;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.framework.mybatis.BaseMapperX;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
public interface SysMenuMapper extends BaseMapperX<SysMenuDO> {


    default List<SysMenuDO> listMenu(MenuListDTO queryDTO) {
        LambdaQueryWrapper<SysMenuDO> queryWrapper = new LambdaQueryWrapperX<SysMenuDO>()
                .likeIfPresent(SysMenuDO::getMenuName, queryDTO.getMenuName())
                .eqIfPresent(SysMenuDO::getStatus, queryDTO.getStatus())
                .orderByAsc(SysMenuDO::getParentId, SysMenuDO::getOrderNum);

        return this.selectList(queryWrapper);
    }

    default List<SysMenuDO> listMenuByIds(MenuListDTO queryDTO, Set<Long> menuIds) {
        LambdaQueryWrapper<SysMenuDO> queryWrapper = new LambdaQueryWrapperX<SysMenuDO>()
                .likeIfPresent(SysMenuDO::getMenuName, queryDTO.getMenuName())
                .eqIfPresent(SysMenuDO::getStatus, queryDTO.getStatus())
                .in(SysMenuDO::getId, menuIds)
                .orderByAsc(SysMenuDO::getParentId, SysMenuDO::getOrderNum);

        return this.selectList(queryWrapper);
    }

//    default List<SysMenuDO> listMenuByUserId(MenuListDTO queryDTO, long userId) {
//        MPJLambdaWrapper<SysMenuDO> wrapper = new MPJLambdaWrapper<SysMenuDO>()
//                .innerJoin(SysRoleMenuDO.class, SysRoleMenuDO::getMenuId, SysMenuDO::getId)
//                .select(SysMenuDO::getId, SysMenuDO::getPerms)
//                .eq(SysRoleMenuDO::getRoleId, roleId)
//                .distinct();
//
//        List<SysMenuDO> menuList = this.selectJoinList(SysMenuDO.class, wrapper);
//    }

    /**
     * 获取所有菜单（不包含按钮）
     * @return
     */
    default List<SysMenuDO> listMenuAll() {
        LambdaQueryWrapper<SysMenuDO> queryWrapper = new LambdaQueryWrapperX<SysMenuDO>()
                .in(SysMenuDO::getMenuType, "M", "C")
                .eq(SysMenuDO::getStatus, "0")
                .orderByAsc(SysMenuDO::getParentId, SysMenuDO::getOrderNum);

        return this.selectList(queryWrapper);
    }

}
