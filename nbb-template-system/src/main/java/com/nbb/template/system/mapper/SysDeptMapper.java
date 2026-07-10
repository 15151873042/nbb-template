package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nbb.template.system.domain.entity.SysDeptDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.framework.mybatis.mapper.BaseMapperX;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
public interface SysDeptMapper extends BaseMapperX<SysDeptDO> {

    /**
     * 通过部门id查询所有子孙部门id
     * @param id 部门id
     * @return 所有子孙部门id
     */
    List<Long> getAllChildrenIdByDeptId(Long id);

    /**
     * 通过部门id查询所有直接子部门id
     * @param id 部门id
     * @return 直接子部门id
     */
    default List<Long> getDirectChildrenIdByDeptId(Long id) {
        LambdaQueryWrapper<SysDeptDO> queryWrapper =
                new LambdaQueryWrapperX<SysDeptDO>().eq(SysDeptDO::getParentId, id)
                        .select(SysDeptDO::getId);
        return selectList(queryWrapper).stream()
                .map(SysDeptDO::getId)
                .collect(Collectors.toList());
    }
}
