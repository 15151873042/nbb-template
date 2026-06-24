package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RoleAddDTO;
import com.nbb.template.system.domain.dto.RolePageDTO;
import com.nbb.template.system.domain.dto.RoleUpdateDTO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.service.SysRoleService;
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
    private SysRoleService sysRoleService;



    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public CommonResult<PageResult<SysRoleDO>> listPage(RolePageDTO dto) {
        PageResult<SysRoleDO> result = sysRoleService.listPageRole(dto);
        return CommonResult.success(result);
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:role:add")
    @PostMapping
    public CommonResult<Void> add(@Validated @RequestBody RoleAddDTO roleAddDTO) {
        sysRoleService.addRole(roleAddDTO);
        return CommonResult.success();
    }

    /**
     * 根据角色编号获取详细信息
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = "/{roleId}")
    public CommonResult<SysRoleDO> getInfo(@PathVariable Long roleId) {
        SysRoleDO sysRoleDO = sysRoleService.getById(roleId);
        return CommonResult.success(sysRoleDO);
    }

    /**
     * 修改保存角色
     */
    @SaCheckPermission("system:role:edit")
    @PutMapping
    public CommonResult<Void> edit(@Validated @RequestBody RoleUpdateDTO updateDTO) {
        sysRoleService.updateRole(updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除角色
     */
    @SaCheckPermission("system:role:remove")
    @DeleteMapping("/{roleIds}")
    public CommonResult<Void> remove(@PathVariable List<Long> roleIds) {
        sysRoleService.deleteByRoleIds(roleIds);
        return CommonResult.success();
    }

}
