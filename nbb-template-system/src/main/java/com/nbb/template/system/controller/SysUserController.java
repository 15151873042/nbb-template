package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.bo.TreeSelectBO;
import com.nbb.template.system.domain.dto.UserPageDTO;
import com.nbb.template.system.domain.entity.SysDeptDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.service.SysDeptService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息
 *
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Resource
    private SysDeptService deptService;

    @Resource
    private SysUserService userService;


    /**
     * 获取用户列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list")
    public CommonResult<PageResult<SysUserDO>> list(UserPageDTO dto) {
        PageResult<SysUserDO> result = userService.selectUserList(dto);
        return CommonResult.success(result);
    }

    /**
     * 获取部门树列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/deptTree")
    public CommonResult<List<TreeSelectBO>> deptTree() {
        return CommonResult.success(deptService.selectDeptTreeList());
    }

}
