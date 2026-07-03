package com.nbb.template.system.domain.dto;

import lombok.Data;

@Data
public class ChangeStatusDTO {

    private Long id;

    /** 状态（0正常 1停用） */
    private String status;
}
