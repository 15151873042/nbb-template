package com.nbb.template.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 胡鹏
 */
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    /** 验证码类型 */
    private static String captchaType;

    public void setCaptchaType(String captchaType) {
        SystemConfig.captchaType = captchaType;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

}
