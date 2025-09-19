package com.nbb.template.system.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nbb.template.system.core.enums.CoreErrorCodeConstants;
import com.nbb.template.system.core.exception.ServiceException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 胡鹏
 */
@Getter
@Setter
public class CommonResult<T> implements Serializable {

    private Integer code;

    private T data;

    private String msg;

    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
    	CommonResult<T> result = new CommonResult<>();
        result.code = CoreErrorCodeConstants.SUCCESS.getCode();
        result.data = data;
    	return result;
    }

    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMsg());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!CoreErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return Objects.equals(code, CoreErrorCodeConstants.SUCCESS.getCode());
    }

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

    public void checkError() {
        if (isError()) {
            throw new ServiceException(code, msg);
        }
    }

    @JsonIgnore
    public T getCheckData() {
        this.checkError();
        return this.data;
    }
}
