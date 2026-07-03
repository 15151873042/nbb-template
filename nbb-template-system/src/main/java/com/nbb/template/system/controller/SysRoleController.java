package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.*;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.service.SysRoleService;
import com.nbb.template.system.service.SysUserService;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息
 *
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Resource
    private SysRoleService roleService;
    @Resource
    private SysUserService userService;

    /**
     * 列表查询
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public CommonResult<PageResult<SysRoleDO>> listPage(RolePageDTO dto) {
        PageResult<SysRoleDO> result = roleService.listPageRole(dto);
        return CommonResult.success(result);
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:role:add")
    @PostMapping
    public CommonResult<Void> add(@Validated @RequestBody RoleAddDTO roleAddDTO) {
        roleService.addRole(roleAddDTO);
        return CommonResult.success();
    }

    /**
     * 根据角色编号获取详细信息
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = "/{roleId}")
    public CommonResult<SysRoleDO> getInfo(@PathVariable Long roleId) {
        SysRoleDO sysRoleDO = roleService.getById(roleId);
        return CommonResult.success(sysRoleDO);
    }

    /**
     * 修改保存角色
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping
    public CommonResult<Void> edit(@Validated @RequestBody RoleUpdateDTO updateDTO) {
        roleService.updateRole(updateDTO);
        return CommonResult.success();
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("@ss.hasPermi('system:role:edit')")
    @PutMapping("/changeStatus")
    public CommonResult<Void> changeStatus(@RequestBody ChangeStatusDTO role) {
        roleService.updateRoleStatus(role);
        return CommonResult.success();
    }

    /**
     * 删除角色
     */
    @SaCheckPermission("system:role:remove")
    @DeleteMapping("/{roleIds}")
    public CommonResult<Void> remove(@PathVariable List<Long> roleIds) {
        roleService.deleteByRoleIds(roleIds);
        return CommonResult.success();
    }

    /**
     * 查询已分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public CommonResult<PageResult<SysUserDO>> allocatedList(RoleAllocatedUserPageDTO pageDTO) {
        PageResult<SysUserDO> result = userService.selectAllocatedList(pageDTO);
        return CommonResult.success(result);
    }


    /**
     * 取消授权用户
     */
    @SaCheckPermission("@ss.hasPermi('system:role:edit')")
    @PutMapping("/authUser/cancel")
    public CommonResult<Void> cancelAuthUser(@RequestBody RoleCancelAuthUserDTO cancelDTO) {
        roleService.deleteAuthUser(cancelDTO);
        return CommonResult.success();
    }


    /**
     * 查询未分配用户角色列表
     */
    @SaCheckPermission("@ss.hasPermi('system:role:list')")
    @GetMapping("/authUser/unallocatedList")
    public CommonResult<PageResult<SysUserDO>> unallocatedList(RoleAllocatedUserPageDTO user) {
        PageResult<SysUserDO> result = userService.selectUnallocatedList(user);
        return CommonResult.success(result);
    }

    /**
     * 批量选择用户授权
     */
    @SaCheckPermission("@ss.hasPermi('system:role:edit')")
    @PutMapping("/authUser/selectAll")
    public CommonResult<Void> selectAuthUserAll(@RequestBody RoleCancelAuthUserDTO dto) {
        roleService.insertAuthUsers(dto);
        return CommonResult.success();
    }

}
