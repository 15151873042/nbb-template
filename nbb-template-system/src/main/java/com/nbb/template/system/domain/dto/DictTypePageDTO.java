package com.nbb.template.system.domain.dto;

import com.nbb.template.system.core.domain.PageParam;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 胡鹏
 */
@Data
public class DictTypePageDTO extends PageParam {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

}
