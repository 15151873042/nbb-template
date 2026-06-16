package com.nbb.template.system.framework.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 会将超长 long 值转换为 string，解决前端 JavaScript 最大安全整数是 2^53-1 的问题
 * @author 胡鹏
 */
public class NumberSerializer extends com.fasterxml.jackson.databind.ser.std.NumberSerializer {

    public static final NumberSerializer INSTANCE = new NumberSerializer(Number.class);

    private NumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
