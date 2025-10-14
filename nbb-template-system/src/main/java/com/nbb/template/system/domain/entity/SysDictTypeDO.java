package com.nbb.template.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbb.template.system.core.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 字典类型表 sys_dict_type
 *
 * @author 胡鹏
 */
@Data
@TableName("sys_dict_type")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDictTypeDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;

    /** 状态（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;

}
