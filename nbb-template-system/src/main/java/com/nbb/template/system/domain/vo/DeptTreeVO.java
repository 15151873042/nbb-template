package com.nbb.template.system.domain.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡鹏
 */
@Data
public class DeptTreeVO {

    /** 部门ID */
    private Long id;

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

    /** 子菜单 */
    private List<DeptTreeVO> children = new ArrayList<>();
}
