package com.nbb.template.system.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class TreeSelectBO {

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 节点禁用 */
    private boolean disabled = false;

    private List<TreeSelectBO> children;

}
