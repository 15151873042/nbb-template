package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.domain.bo.TreeSelectBO;
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
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> listMenuPermsByUserId(Long userId);

    /**
     * 根据角色ID查询所关联的菜单id
     *
     * @param roleId 角色ID
     * @return 关联的菜单id
     */
    List<Long> listMenuIdByRoleId(Long roleId);

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

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelectBO> buildMenuTreeSelect(List<SysMenuDO> menus);

    void deleteById(Long id);

    void updateMenu(MenuUpdateDTO menu);
}
