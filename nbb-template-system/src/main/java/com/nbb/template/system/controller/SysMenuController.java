package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import com.nbb.template.system.constant.SystemDictConstants;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.utils.StrUtil;
import com.nbb.template.system.domain.bo.TreeSelectBO;
import com.nbb.template.system.domain.dto.MenuAddDTO;
import com.nbb.template.system.domain.dto.MenuListDTO;
import com.nbb.template.system.domain.dto.MenuUpdateDTO;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.vo.RoleMenuTreeSelectVO;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单信息
 *
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleService roleService;

    /**
     * 获取菜单列表
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    public CommonResult<List<SysMenuDO>> list(MenuListDTO queryDTO) {
        List<SysMenuDO> menus = menuService.listMenu(queryDTO);
        return CommonResult.success(menus);
    }

    /**
     * 新增菜单
     */
    @SaCheckPermission("system:menu:add")
    @PostMapping
    public CommonResult<Void> add(@Validated @RequestBody MenuAddDTO menu) {
        if (SystemDictConstants.YES_FRAME.equals(menu.getIsFrame()) && !StrUtil.ishttp(menu.getPath())) {
            return CommonResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }

        menuService.addMenu(menu);
        return CommonResult.success();
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public CommonResult<SysMenuDO> getInfo(@PathVariable Long menuId) {
        SysMenuDO menu = menuService.getById(menuId);
        return CommonResult.success(menu);
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public CommonResult<List<TreeSelectBO>> treeselect() {
        List<SysMenuDO> menus = menuService.listMenu(new MenuListDTO());
        return CommonResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public CommonResult<RoleMenuTreeSelectVO> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenuDO> menus = menuService.listMenu(new MenuListDTO());
        List<TreeSelectBO> treeSelect = menuService.buildMenuTreeSelect(menus);
        Set<Long> menuIds = roleService.listMenuIdById(roleId);

        RoleMenuTreeSelectVO vo = new RoleMenuTreeSelectVO();
        vo.setMenus(treeSelect);
        vo.setCheckedKeys(ListUtil.toList(menuIds));
        return CommonResult.success(vo);
    }



    /**
     * 修改菜单
     */
    @SaCheckPermission("system:menu:edit")
    @PutMapping
    public CommonResult<Void> edit(@Validated @RequestBody MenuUpdateDTO menu) {
        menuService.updateMenu(menu);
        return CommonResult.success();
    }

    /**
     * 删除菜单
     */
    @SaCheckPermission("@ss.hasPermi('system:menu:remove')")
    @DeleteMapping("/{menuId}")
    public CommonResult<Void> remove(@PathVariable("menuId") Long menuId) {
        menuService.deleteById(menuId);
        return CommonResult.success();
    }
}
