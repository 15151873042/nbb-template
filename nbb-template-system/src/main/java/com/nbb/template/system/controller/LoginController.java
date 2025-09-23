package com.nbb.template.system.controller;

import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.vo.LoginVO;
import com.nbb.template.system.service.LoginService;
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

    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody LoginDTO dto) {
        LoginVO vo = loginService.login(dto);
        return CommonResult.success(vo);
    }
}
