package com.nbb.template.system.domain.dto;

import com.nbb.template.system.core.domain.PageParam;
import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class RoleAllocatedUserPageDTO extends PageParam {

    /** 角色ID */
    private Long roleId;

    /** 用户账号 */
    private String userName;

    /** 手机号码 */
    private String phonenumber;
}
