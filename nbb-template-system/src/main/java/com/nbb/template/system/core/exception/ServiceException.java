package com.nbb.template.system.core.exception;

import com.nbb.template.system.core.domain.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 胡鹏
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException{

    private Integer code;

    private String msg;

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}
