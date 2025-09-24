package com.nbb.template.system.framework.web.handler;

import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 胡鹏
 */
@Slf4j
@RestControllerAdvice
public class SystemExceptionHandler {

    /**
     * 处理业务异常 ServiceException
     *
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceExceptionHandler(ServiceException ex) {
        return CommonResult.error(ex.getCode(), ex.getMsg());
    }
}
