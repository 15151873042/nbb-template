package com.nbb.template.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.vo.LoginVO;
import com.nbb.template.system.domain.vo.UserPermissionInfoVO;
import com.nbb.template.system.service.LoginService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 胡鹏
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;
    @Resource
    private SysUserService sysUserService;

    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody LoginDTO dto) {
        LoginVO vo = loginService.login(dto);
        return CommonResult.success(vo);
    }

    @PostMapping("/logout")
    public CommonResult<Void> logout() {
        StpUtil.logout();
        return CommonResult.success();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public CommonResult<UserPermissionInfoVO> getInfo() {
        long loginUserId = StpUtil.getLoginIdAsLong();
        SysUserDO userBasicInfo = sysUserService.getUserBasicInfo(loginUserId);

        UserPermissionInfoVO vo = UserPermissionInfoVO.builder()
                .user(userBasicInfo)
                .roles(CollUtil.newHashSet("*:*:*"))
                .permissions(CollUtil.newHashSet("*:*:*"))
                .build();

        return CommonResult.success(vo);
    }
}
