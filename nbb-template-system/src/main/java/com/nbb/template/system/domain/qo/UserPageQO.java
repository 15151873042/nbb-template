package com.nbb.template.system.domain.qo;

import com.nbb.template.system.core.domain.PageParam;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 胡鹏
 */
@Data
public class UserPageQO extends PageParam {

    /** 部门ID */
    private Long deptId;

    /** 子部门ID */
    private List<Long> deptIds;

    /** 用户账号 */
    private String userName;

    /** 手机号码 */
    private String phonenumber;

    /** 账号状态（0正常 1停用） */
    private String status;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;
}
