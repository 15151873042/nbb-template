package com.nbb.template.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.vo.LoginVO;
import com.nbb.template.system.domain.vo.RouterVO;
import com.nbb.template.system.domain.vo.UserPermissionInfoVO;
import com.nbb.template.system.service.LoginService;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysPermissionService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author 胡鹏
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysPermissionService permissionService;
    @Resource
    private SysMenuService menuService;


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
        // 角色集合
        Set<String> roles = permissionService.listRolePermission(loginUserId);
        // 角色集合
        Set<String> permissions = permissionService.listMenuPermission(loginUserId);

        UserPermissionInfoVO vo = UserPermissionInfoVO.builder()
                .user(userBasicInfo)
                .roles(roles)
                .permissions(permissions)
                .build();

        return CommonResult.success(vo);
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public CommonResult<RouterVO> getRouters() {
        long loginUserId = StpUtil.getLoginIdAsLong();
        List<SysMenuDO> routers = menuService.getMenuTreeByUserId(loginUserId);
        return CommonResult.success(null);
    }
}
