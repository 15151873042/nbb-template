package com.nbb.template.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbb.template.system.core.domain.LogicDeleteBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author 胡鹏
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserDO extends LogicDeleteBaseDO {

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别 */
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 账号状态（0正常 1停用） */
    private String status;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private Date loginDate;

    /** 密码最后更新时间 */
    private Date pwdUpdateDate;

    /** 备注 */
    private String remark;
}
