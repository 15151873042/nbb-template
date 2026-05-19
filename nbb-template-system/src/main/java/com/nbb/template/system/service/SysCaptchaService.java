package com.nbb.template.system.service;

import com.nbb.template.system.domain.vo.CaptchaImageVO;

public interface SysCaptchaService {

    /**
     * 生成验证码
     * @return
     */
    CaptchaImageVO getCode();

    /**
     * 校验验证码
     * @param code 验证码
     * @param uuid 后端缓存验证码的 Key
     */
    void verifyCode(String code, String uuid);
}
