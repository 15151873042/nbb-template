package com.nbb.template.system.domain.dto;

import com.nbb.template.system.core.domain.PageParam;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 胡鹏
 */
@Data
public class UserPageDTO extends PageParam {

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    private String userName;

    /** 手机号码 */
    private String phonenumber;

    /** 账号状态（0正常 1停用） */
    private String status;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;
}
