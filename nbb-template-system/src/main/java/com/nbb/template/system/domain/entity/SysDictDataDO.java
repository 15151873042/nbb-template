package com.nbb.template.system.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.nbb.template.system.core.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 字典数据表 sys_dict_data
 *
 * @author 胡鹏
 */
@Data
@TableName("sys_dict_data")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDictDataDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    /** 是否默认（Y是 N否） */
    private String isDefault;

    /** 状态（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;
}
