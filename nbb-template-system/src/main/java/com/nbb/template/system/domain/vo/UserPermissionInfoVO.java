package com.nbb.template.system.domain.vo;

import com.nbb.template.system.domain.entity.SysUserDO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author 胡鹏
 */
@Builder
@Data
public class UserPermissionInfoVO {

    private SysUserDO user;

    private Set<String> roles;

    private Set<String> permissions;
}
