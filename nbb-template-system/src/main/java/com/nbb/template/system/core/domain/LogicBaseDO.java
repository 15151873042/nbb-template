package com.nbb.template.system.core.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class LogicBaseDO extends BaseDO {

    @TableLogic
    private Long deleted;
}
