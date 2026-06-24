package com.nbb.template.system.domain.vo;

import com.nbb.template.system.domain.bo.TreeSelectBO;
import lombok.Data;

import java.util.List;

@Data
public class RoleMenuTreeSelectVO {

    /** 菜单树 */
    private List<TreeSelectBO> menus;

    /** 已选择的菜单ID列表 */
    private List<Long> checkedKeys;

}
