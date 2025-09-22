package com.nbb.template.system.framework.mybatis;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.util.StringUtils;

/**
 * @author 胡鹏
 */
public class LambdaQueryWrapperX<T> extends LambdaQueryWrapper<T> {

    public LambdaQueryWrapperX<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (LambdaQueryWrapperX<T>) super.eq(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperX<T>) super.like(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (LambdaQueryWrapperX)super.gt(column, val) : this;
    }

    public LambdaQueryWrapperX<T> geIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (LambdaQueryWrapperX)super.ge(column, val) : this;
    }

    public LambdaQueryWrapperX<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (LambdaQueryWrapperX)super.lt(column, val) : this;
    }

    public LambdaQueryWrapperX<T> leIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (LambdaQueryWrapperX)super.le(column, val) : this;
    }

}
