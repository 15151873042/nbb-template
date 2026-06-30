package com.nbb.template.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.vo.LoginVO;
import com.nbb.template.system.domain.vo.MenuTreeVO;
import com.nbb.template.system.domain.vo.RouterVO;
import com.nbb.template.system.domain.vo.UserPermissionInfoVO;
import com.nbb.template.system.service.LoginService;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysSessionService;
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
    private SysMenuService menuService;
    @Resource
    private SysSessionService sessionService;


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
     * 获取用户信息（前端强刷页面的时候都会调用该接口）
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public CommonResult<UserPermissionInfoVO> getInfo() {
        // 刷新用户session（将用户最新角色，权限标识放入session）
        sessionService.refreshUserSession();

        // 用户基本信息
        SysUserDO userBasicInfo = sessionService.getUserInfo();
        // 角色集合
        Set<String> roles = sessionService.listRolePermission();
        // 权限集合
        Set<String> permissions = sessionService.listMenuPermission();

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
    public CommonResult<List<RouterVO>> getRouters() {
        long loginUserId = StpUtil.getLoginIdAsLong();
        List<MenuTreeVO> menuTreeList = menuService.getMenuTreeByUserId(loginUserId);
        List<RouterVO> routerList = menuService.buildMenus(menuTreeList);
        return CommonResult.success(routerList);
    }
}
