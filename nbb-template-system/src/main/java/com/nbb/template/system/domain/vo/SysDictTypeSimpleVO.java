package com.nbb.template.system.domain.vo;

import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class SysDictTypeSimpleVO {

    /** 主键 */
    private Long id;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;
}
