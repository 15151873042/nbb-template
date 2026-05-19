package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.dto.RolePageDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色信息
 *
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {


    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public CommonResult<Void> list(RolePageDTO role) {
        return CommonResult.success();
    }

}
