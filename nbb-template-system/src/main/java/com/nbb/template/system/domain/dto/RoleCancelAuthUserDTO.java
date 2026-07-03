package com.nbb.template.system.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleCancelAuthUserDTO {

    private Long roleId;

    private List<Long> userIds;
}
