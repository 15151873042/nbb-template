package com.nbb.template.system.framework.web.converter;

import com.nbb.template.system.core.utils.StrUtil;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * x-www-form-urlencoded传参，date类型数据转换适配
 */
public class StringToDateConverter implements Converter<String, Date>{

    @SneakyThrows
    @Override
    public Date convert(String source) {
        if (StrUtil.isEmpty(source)) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
    }
}
