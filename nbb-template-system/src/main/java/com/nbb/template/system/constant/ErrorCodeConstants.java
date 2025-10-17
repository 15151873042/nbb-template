package com.nbb.template.system.constant;

import com.nbb.template.system.core.domain.ErrorCode;

/**
 * @author 胡鹏
 */
public interface ErrorCodeConstants {

    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1_002_006_005, "无法删除，【{0}】字典类型还有字典数据");
}
