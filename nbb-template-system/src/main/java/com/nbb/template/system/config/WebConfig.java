package com.nbb.template.system.config;

import com.nbb.template.system.framework.web.converter.StringToLocalDateTimeConverter;
import com.nbb.template.system.framework.web.converter.StringToDateConverter;
import com.nbb.template.system.framework.web.converter.StringToLocalDateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 胡鹏
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     * 添加数据类型转换器，用于x-www-form-urlencoded传参
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
    }
}
