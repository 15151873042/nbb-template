package com.nbb.template.system.domain.dto;

import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class DictDataUpdateDTO {

    /** 主键 */
    private Long id;

    /** 字典排序 */
    private Long dictSort;

    /** 字典标签 */
    private String dictLabel;

    /** 字典键值 */
    private String dictValue;

    /** 字典类型 */
    private String dictType;

    /** 样式属性（其他样式扩展） */
    private String cssClass;

    /** 表格字典样式 */
    private String listClass;

    /** 状态（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;
}
