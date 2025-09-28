package com.nbb.template.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author 胡鹏
 */
@Data
@TableName("sys_user_role")
@EqualsAndHashCode
@ToString
public class SysUserRoleDO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
