package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.domain.dto.MenuAddDTO;
import com.nbb.template.system.domain.dto.MenuListDTO;
import com.nbb.template.system.domain.dto.MenuUpdateDTO;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.vo.MenuTreeVO;
import com.nbb.template.system.domain.vo.RouterVO;

import java.util.List;
import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysMenuService extends IService<SysMenuDO> {

    /**
     * 根据用户查询系统菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenuDO> listMenu(MenuListDTO queryDTO);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    void addMenu(MenuAddDTO menu);

    /**
     * 校验菜单名称是否唯一
     * @param menuName 菜单名称
     * @param excludeMenuId 需要排除的菜单id
     * @return
     */
    boolean isMenuNameUnique(String menuName, Long... excludeMenuId);


    /**
     *  获取角色所拥有菜单权限标识
     * @param roleId 角色id
     * @return
     */
    Set<String> listPermsByRoleId(Long roleId);

    /**
     *  获取所有角色的菜单
     * @param roleId 角色id
     * @return
     */
    List<SysMenuDO> listMenuByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuTreeVO> getMenuTreeByUserId(long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVO> buildMenus(List<MenuTreeVO> menus);

    void deleteById(Long id);

    void updateMenu(MenuUpdateDTO menu);
}
