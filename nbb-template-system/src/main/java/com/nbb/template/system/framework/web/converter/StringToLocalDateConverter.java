package com.nbb.template.system.framework.web.converter;

import com.nbb.template.system.core.utils.StrUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * x-www-form-urlencoded传参，LocalDate类型数据转换适配
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate>{

    @Override
    public LocalDate convert(String source) {
        if (StrUtil.isEmpty(source)) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
