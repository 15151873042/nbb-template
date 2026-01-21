package com.nbb.template.system.domain.dto;

import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class MenuListDTO {

    /** 菜单名称 */
    private String menuName;

    /** 菜单状态（0正常 1停用） */
    private String status;

}
