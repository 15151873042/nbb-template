package com.nbb.template.system.framework.mybatis;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.util.StringUtils;

/**
 * @author 胡鹏
 */
public class MPJLambdaWrapperX<T> extends MPJLambdaWrapper<T> {

    public MPJLambdaWrapperX<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (MPJLambdaWrapperX<T>) super.eq(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (MPJLambdaWrapperX<T>) super.like(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (MPJLambdaWrapperX) super.gt(column, val) : this;
    }

    public MPJLambdaWrapperX<T> geIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (MPJLambdaWrapperX) super.ge(column, val) : this;
    }

    public MPJLambdaWrapperX<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (MPJLambdaWrapperX) super.lt(column, val) : this;
    }

    public MPJLambdaWrapperX<T> leIfPresent(SFunction<T, ?> column, Object val) {
        return val != null ? (MPJLambdaWrapperX) super.le(column, val) : this;
    }
}
