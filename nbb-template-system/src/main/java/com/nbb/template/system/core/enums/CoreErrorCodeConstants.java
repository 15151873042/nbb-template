package com.nbb.template.system.core.enums;

import com.nbb.template.system.core.domain.ErrorCode;

/**
 * @author 胡鹏
 */
public interface CoreErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(0, "成功");

    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "系统异常");

    ErrorCode DATA_EXIST = new ErrorCode(1001, "数据已存在");

    ErrorCode FAIL_ACQUIRE_LOCK = new ErrorCode(1002, "获取锁失败");

    ErrorCode LINKED_DATA = new ErrorCode(1003, "当前数据已绑定其他相关数据，暂时不能删除");

}
