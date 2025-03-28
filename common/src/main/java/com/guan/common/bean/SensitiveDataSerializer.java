package com.guan.common.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.guan.common.constant.BaseConstants;

import java.io.IOException;
import java.util.regex.Matcher;

public class SensitiveDataSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        if (value.isEmpty()) {
            gen.writeString("");
            return;
        }
        // 获取当前要序列化的字段名称
        String currentPropertyName = gen.getOutputContext().getCurrentName();
        if ("name".equals(currentPropertyName)) {
            // 名字脱敏逻辑，这里简单示例只显示第一个字，其余用*代替
            if (value.length() == 1) {
                gen.writeString(value);
            } else {
                gen.writeString(value.charAt(0) + "*".repeat(value.length() - 1));
            }
        } else if ("email".equals(currentPropertyName)) {
            Matcher matcher = BaseConstants.EMAIL_DESENSITIVE_REGEX.matcher(value);
            if (matcher.find()) {
                String desensitizedEmail = "****@****." + matcher.group(3);
                gen.writeString(desensitizedEmail);
            } else {
                gen.writeString(value);
            }
        } else {
            gen.writeString(value);
        }
    }
}
