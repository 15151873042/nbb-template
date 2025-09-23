package com.nbb.template.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.vo.LoginVO;
import com.nbb.template.system.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * @author 胡鹏
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginVO login(LoginDTO dto) {
        StpUtil.login("user-id-001");
        String tokenValue = StpUtil.getTokenValue();
        return LoginVO.builder()
                .token(tokenValue)
                .build();
    }
}
