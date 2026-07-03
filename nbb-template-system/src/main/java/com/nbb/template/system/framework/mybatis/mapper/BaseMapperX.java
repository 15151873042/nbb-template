package com.nbb.template.system.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.interfaces.MPJBaseJoin;
import com.nbb.template.system.core.domain.PageParam;
import com.nbb.template.system.core.domain.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface BaseMapperX<T> extends MPJBaseMapper<T> {

    default PageResult<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
        // 特殊：不分页，直接查询全部
        if (PageParam.PAGE_SIZE_NONE.equals(pageParam.getPageSize())) {
            List<T> list = selectList(queryWrapper);
            return new PageResult<>(list, (long) list.size());
        }

        Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        selectPage(page, queryWrapper);

        return new PageResult<>(page.getRecords(), page.getTotal());
    }


    default <DTO> PageResult<DTO>  selectJoinPage(PageParam pageParam, Class<DTO> clazz, @Param(Constants.WRAPPER) MPJBaseJoin<T> wrapper) {
        // 特殊：不分页，直接查询全部
        if (PageParam.PAGE_SIZE_NONE.equals(pageParam.getPageSize())) {
            List<DTO> list = selectJoinList(clazz, wrapper);
            return new PageResult<>(list, (long) list.size());
        }

        Page<DTO> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        selectJoinPage(page, clazz, wrapper);

        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    default T selectOne(SFunction<T, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field, value));
    }

}
