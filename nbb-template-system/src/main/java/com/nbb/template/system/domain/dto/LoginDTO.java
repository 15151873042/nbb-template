package com.nbb.template.system.domain.dto;

import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class LoginDTO {
    /** 用户名 */
    private String username;

    /** 用户密码 */
    private String password;

    /** 验证码值 */
    private String code;

    /** 验证码在后端存储的key */
    private String uuid;

}
