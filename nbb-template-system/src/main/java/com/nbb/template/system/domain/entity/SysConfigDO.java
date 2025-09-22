package com.nbb.template.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbb.template.system.core.domain.BaseDO;
import lombok.Data;


/**
 * @author 胡鹏
 */
@Data
@TableName("sys_config")
public class SysConfigDO extends BaseDO {

    /** 参数名称 */
    private String configName;

    /** 参数键名 */
    private String configKey;

    /** 参数键值 */
    private String configValue;

    /** 是否系统内置 */
    private Boolean internal;

    private String remark;

}
