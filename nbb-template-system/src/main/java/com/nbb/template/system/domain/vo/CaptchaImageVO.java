package com.nbb.template.system.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
@Builder
public class CaptchaImageVO {

    /** 是否开启验证码 */
    private Boolean captchaEnabled;

    /** 验证码图片（base64） */
    private String img;

    /** 后端缓存验证码的 Key */
    private String uuid;
}
