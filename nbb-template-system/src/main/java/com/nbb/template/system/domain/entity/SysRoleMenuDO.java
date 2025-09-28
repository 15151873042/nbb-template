package com.nbb.template.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author 胡鹏
 */
@Data
@TableName("sys_role_menu")
@EqualsAndHashCode
@ToString
public class SysRoleMenuDO {
    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;

}
