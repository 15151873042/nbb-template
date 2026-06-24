package com.nbb.template.system.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleAddDTO {

    /** 角色名称 */
    private String roleName;

    /** 角色权限 */
    private String roleKey;

    /** 角色排序 */
    private Integer roleSort;

    /** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
    private boolean menuCheckStrictly;

    /** 角色状态（0正常 1停用） */
    private String status;

    /** 授权的菜单id */
    private List<Long> menuIds;

}
