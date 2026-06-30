package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.domain.dto.MenuListDTO;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.qo.MenuListQO;
import com.nbb.template.system.framework.mybatis.mapper.BaseMapperX;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysMenuMapper extends BaseMapperX<SysMenuDO> {

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenuDO> listMenuTreeByUserId(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param qo 查询信息
     * @return 菜单列表
     */
    List<SysMenuDO> listMenuByUserId(MenuListQO qo);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> listMenuPermsByUserId(Long userId);

    default List<SysMenuDO> listMenu(MenuListDTO queryDTO) {
        LambdaQueryWrapper<SysMenuDO> queryWrapper = new LambdaQueryWrapperX<SysMenuDO>()
                .likeIfPresent(SysMenuDO::getMenuName, queryDTO.getMenuName())
                .eqIfPresent(SysMenuDO::getStatus, queryDTO.getStatus())
                .orderByAsc(SysMenuDO::getParentId, SysMenuDO::getOrderNum);

        return this.selectList(queryWrapper);
    }

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
