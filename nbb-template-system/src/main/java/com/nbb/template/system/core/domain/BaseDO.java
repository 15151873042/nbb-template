package com.nbb.template.system.core.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 胡鹏
 */
@Data
public class BaseDO {

    private Long id;

    private LocalDateTime createTime;

    private Long createId;

    private LocalDateTime updateTime;

    private Long updateId;
}
