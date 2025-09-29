package com.nbb.template.system.config;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nbb.template.system.framework.jackson.BigDecimalSerializer;
import com.nbb.template.system.framework.jackson.NumberSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class JacksonConfig {

    /**
     * ObjectMapper已经在JacksonAutoConfiguration自动状态，此处只需要自定义Jackson2ObjectMapperBuilder即可
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeObjectMapper() {
        return jacksonObjectMapperBuilder -> {
            // 设置LocalDateTime序列化方式
            DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormatter));
            // 设置LocalDate序列化方式
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new LocalDateSerializer(localDateFormatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDate.class, new LocalDateDeserializer(localDateFormatter));
            // 设置Date序列化方式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jacksonObjectMapperBuilder.serializerByType(Date.class, new DateSerializer(false, simpleDateFormat));
            jacksonObjectMapperBuilder.deserializerByType(Date.class, new DateDeserializer(DateDeserializer.instance, simpleDateFormat, "yyyy-MM-dd HH:mm:ss"));
            // Long类型转换成String，已避免（将超过16位的long，前端精度丢失）
            jacksonObjectMapperBuilder.serializerByType(Long.class, NumberSerializer.INSTANCE);
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, NumberSerializer.INSTANCE);
            jacksonObjectMapperBuilder.serializerByType(BigDecimal.class, BigDecimalSerializer.INSTANCE);
        };
    }
}
