package com.xudis.iam.config;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson 配置
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Configuration
public class JacksonConfig {

    /**
     * 上海时区
     */
    private static final ZoneId SHANGHAI_ZONE = ZoneId.of("Asia/Shanghai");

    /**
     * 日期时间格式
     */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 注册 JavaTimeModule 以支持 Java 8 日期时间类型
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 添加自定义的 LocalDateTime 序列化器和反序列化器
        javaTimeModule.addSerializer(LocalDateTime.class, new ShanghaiLocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new ShanghaiLocalDateTimeDeserializer());

        objectMapper.registerModule(javaTimeModule);

        // 其他配置
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper;
    }

    /**
     * 自定义 LocalDateTime 反序列化器
     * 支持接收各种时区的时间，统一转换为上海时区
     */
    public static class ShanghaiLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

        public ShanghaiLocalDateTimeDeserializer() {
            super(LocalDateTime.class);
        }

        @Override
        public LocalDateTime deserialize(com.fasterxml.jackson.core.JsonParser p,
                                        DeserializationContext ctxt) throws IOException {
            String dateString = p.getValueAsString();

            try {
                // 尝试解析 ISO 8601 格式（带时区）
                if (dateString.contains("T") || dateString.contains("Z") || dateString.contains("+")) {
                    // 解析为 ZonedDateTime，支持各种时区
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString);
                    // 转换为上海时区的 LocalDateTime
                    return zonedDateTime.withZoneSameInstant(SHANGHAI_ZONE).toLocalDateTime();
                }

                // 尝试解析标准格式（不带时区，默认为上海时区）
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                return LocalDateTime.parse(dateString, formatter);

            } catch (Exception e) {
                throw new IOException("无法解析日期时间: " + dateString + ", 请使用 ISO 8601 格式或 " + DATE_TIME_FORMAT + " 格式", e);
            }
        }
    }

    /**
     * 自定义 LocalDateTime 序列化器
     * 输出上海时区的时间
     */
    public static class ShanghaiLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

        public ShanghaiLocalDateTimeSerializer() {
            super(LocalDateTime.class);
        }

        @Override
        public void serialize(LocalDateTime value,
                             com.fasterxml.jackson.core.JsonGenerator gen,
                             SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
                return;
            }

            // 格式化为上海时区的时间字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            gen.writeString(value.format(formatter));
        }
    }
}
