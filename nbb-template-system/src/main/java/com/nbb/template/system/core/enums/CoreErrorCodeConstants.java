package com.nbb.template.system.core.enums;

import com.nbb.template.system.core.domain.ErrorCode;

/**
 * @author 胡鹏
 */
public interface CoreErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(0, "成功");

    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "系统异常");

}
