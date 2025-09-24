package com.nbb.template.system.core.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author 胡鹏
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LogicDeleteBaseDO extends BaseDO {

    /** 是否删除：0未删除，非0则为删除 */
    @TableLogic
    private Long deleted;

}
