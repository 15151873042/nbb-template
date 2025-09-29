package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {

    /**
     * 获取所有菜单（不包含按钮）
     * @return
     */
    default List<SysMenuDO> listMenuAll() {
        LambdaQueryWrapper<SysMenuDO> queryWrapper = new LambdaQueryWrapperX<SysMenuDO>()
                .in(SysMenuDO::getMenuType, "M", "C")
                .eq(SysMenuDO::getStatus, "0")
                .orderByAsc(SysMenuDO::getParentId, SysMenuDO::getOrderNum);

        return this.selectList(queryWrapper);
    }
}
