package com.nbb.template.system.core.utils;

import com.nbb.template.system.core.domain.ErrorCode;
import com.nbb.template.system.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

import static com.nbb.template.system.core.enums.CoreErrorCodeConstants.DATA_EXIST;

/**
 *
 */
@Slf4j
public class ServiceExceptionUtil {

    public static ServiceException dataExistException() {
        return new ServiceException(DATA_EXIST);
    }

    public static ServiceException exception(ErrorCode errorCode, Object... params) {
        return exception0(errorCode.getCode(), errorCode.getMsg(), params);
    }
    public static ServiceException exception0(Integer code, String messagePattern, Object... params) {

        String message = doFormat(messagePattern, params);
        return new ServiceException(code, message);
    }

    public static String doFormat(String messagePattern, Object... params) {
        return MessageFormat.format(messagePattern, params);
    }

}
