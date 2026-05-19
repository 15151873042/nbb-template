package com.nbb.template.system.framework.web.converter;

import com.nbb.template.system.core.utils.StrUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * x-www-form-urlencoded传参，LocalDateTime类型数据转换适配
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime>{

    @Override
    public LocalDateTime convert(String source) {
        if (StrUtil.isEmpty(source)) {
            return null;
        }
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
