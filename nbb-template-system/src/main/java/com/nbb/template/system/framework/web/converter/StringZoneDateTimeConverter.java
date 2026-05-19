package com.nbb.template.system.framework.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * x-www-form-urlencoded传参，前端传递携带时区信息的LocalDateTime（例如2026-05-15T13:30:30+0800）类型数据转换适配
 */
public class StringZoneDateTimeConverter implements Converter<String, LocalDateTime>{

    @Override
    public LocalDateTime convert(String source) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
        return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}
