package com.nbb.template.system.domain.dto;

import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class DictTypeUpdateDTO {

    /** 主键 */
    private Long id;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;

    /** 状态（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;
}
