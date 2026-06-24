package com.nbb.template.system.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class MenuTreeBO {

    private Long id;

    /** 菜单名称 */
    private String menuName;

    private List<MenuTreeBO> children;
}
