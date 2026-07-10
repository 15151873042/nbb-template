package com.nbb.template.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbb.template.system.core.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 部门表 sys_dept
 *
 * @author 胡鹏
 */
@Data
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysDeptDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
}
