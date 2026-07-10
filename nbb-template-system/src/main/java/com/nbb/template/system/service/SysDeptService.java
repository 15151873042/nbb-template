package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.bo.TreeSelectBO;
import com.nbb.template.system.domain.dto.RoleAllocatedUserPageDTO;
import com.nbb.template.system.domain.entity.SysDeptDO;
import com.nbb.template.system.domain.entity.SysUserDO;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface SysDeptService extends IService<SysDeptDO> {

    /**
     * 查询部门树结构信息
     *
     * @return 部门树信息集合
     */
    List<TreeSelectBO> selectDeptTreeList();
}
