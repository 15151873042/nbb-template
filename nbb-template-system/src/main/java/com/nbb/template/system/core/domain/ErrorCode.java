package com.nbb.template.system.core.domain;

import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class ErrorCode {

    private final Integer code;

    private final String msg;

    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
