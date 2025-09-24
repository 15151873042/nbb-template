package com.nbb.template.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.nbb.template.system.core.exception.ServiceException;
import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.vo.LoginVO;
import com.nbb.template.system.service.LoginService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 胡鹏
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    SysUserService sysUserService;

    @Override
    public LoginVO login(LoginDTO dto) {
        // 通过账号获取用户信息
        SysUserDO sysUserDO = sysUserService.selectUserByUserName(dto.getUsername());
        // 校验密码
        if (!BCrypt.checkpw(dto.getPassword(), sysUserDO.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        // 登录
        StpUtil.login(sysUserDO.getId());

        // 获取当前登录态对应的 token
        String tokenValue = StpUtil.getTokenValue();

        return LoginVO.builder().token(tokenValue).build();
    }


    public static void main(String[] args) {
        // 明文密码
        String password = "admin123";

        // 1. 加密密码（自动生成盐值和默认工作因子）
        String hash = BCrypt.hashpw(password);
        String hash2 = BCrypt.hashpw(password);
        System.out.println("加密后的哈希值：" + hash);
        System.out.println("加密后的哈希值：" + hash2);

        // 2. 验证密码（正确密码）
        boolean isMatch = BCrypt.checkpw(password, hash);
        boolean isMatch2 = BCrypt.checkpw(password, hash2);
        System.out.println("密码匹配吗？" + isMatch); // 输出：true
        System.out.println("密码匹配吗？" + isMatch2); // 输出：true

        // 3. 验证错误密码
        boolean isWrongMatch = BCrypt.checkpw("wrongPassword", hash);
        System.out.println("错误密码匹配吗？" + isWrongMatch); // 输出：false
    }
}
