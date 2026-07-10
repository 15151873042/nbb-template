package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.template.system.domain.bo.TreeSelectBO;
import com.nbb.template.system.domain.entity.SysDeptDO;
import com.nbb.template.system.domain.vo.DeptTreeVO;
import com.nbb.template.system.domain.vo.MenuTreeVO;
import com.nbb.template.system.mapper.SysDeptMapper;
import com.nbb.template.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptDO> implements SysDeptService {

    @Resource
    private SysDeptMapper deptMapper;

    @Override
    public List<TreeSelectBO> selectDeptTreeList() {
        List<SysDeptDO> deptDOList = deptMapper.selectList();
        List<DeptTreeVO> deptTreeVOS = BeanUtil.copyToList(deptDOList, DeptTreeVO.class);

        List<DeptTreeVO> deptTrees = this.buildDeptTree(deptTreeVOS);
        return deptTrees.stream().map(this::toTreeSelect).collect(Collectors.toList());
    }

    private TreeSelectBO toTreeSelect(DeptTreeVO deptTree) {
        TreeSelectBO treeSelect = new TreeSelectBO();
        treeSelect.setId(deptTree.getId());
        treeSelect.setLabel(deptTree.getDeptName());
        List<TreeSelectBO> children = deptTree.getChildren().stream().map(this::toTreeSelect).collect(Collectors.toList());
        treeSelect.setChildren(children);
        return treeSelect;
    }

    private List<DeptTreeVO> buildDeptTree(List<DeptTreeVO> depts) {
        List<DeptTreeVO> returnList = new ArrayList<>();
        List<Long> tempList = depts.stream().map(DeptTreeVO::getId).collect(Collectors.toList());

        for (DeptTreeVO dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t    子节点
     */
    private void recursionFn(List<DeptTreeVO> list, DeptTreeVO t) {
        // 得到子节点列表
        List<DeptTreeVO> childList = getChildList(list, t);

        t.setChildren(childList);

        for (DeptTreeVO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<DeptTreeVO> getChildList(List<DeptTreeVO> list, DeptTreeVO t) {
        return list.stream()
                .filter(menu -> menu.getParentId().longValue() == t.getId().longValue())
                .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<DeptTreeVO> list, DeptTreeVO t) {
        return getChildList(list, t).size() > 0;
    }




}
