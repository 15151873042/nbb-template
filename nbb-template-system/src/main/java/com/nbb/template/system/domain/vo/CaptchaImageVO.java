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

    /** 该验证码的唯一标识（用户登陆时校验使用） */
    private String uuid;
}
