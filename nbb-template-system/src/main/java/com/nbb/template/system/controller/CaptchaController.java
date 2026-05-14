package com.nbb.template.system.controller;

import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.vo.CaptchaImageVO;
import com.nbb.template.system.service.SysCaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 验证码操作处理
 *
 * @author 胡鹏
 */
@RestController
public class CaptchaController {

    @Resource
    private SysCaptchaService sysCaptchaService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public CommonResult<CaptchaImageVO> getCode() {
        return CommonResult.success(sysCaptchaService.getCode());
    }
}
