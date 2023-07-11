package com.tanchengjin.cms.annotations.dict;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.tanchengjin.cms.modules.article.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 功能描述 : 配置类
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
    private final static Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    @Autowired
    private ArticleService articleService;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullStringAsEmpty);
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        fastJsonConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8,
                new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8),
                new MediaType(MediaType.APPLICATION_FORM_URLENCODED, StandardCharsets.UTF_8)));

        // 序列化配置
        SerializeConfig serializeConfig = new SerializeConfig() {
            @Override
            public ObjectSerializer getObjectWriter(Class<?> clazz) {
                // 从类里面寻找注解，如果字段上包含枚举序列化注解的话，我们就走到
                final Dict annotation = findClassFiledAnnotation(clazz, Dict.class);
                if (annotation != null) {
                    return new DictSerializer(articleService);
                }
                return super.getObjectWriter(clazz);
            }
        };
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonConverter);
        super.configureMessageConverters(converters);
    }

    public static <A extends Annotation> A findClassFiledAnnotation(Class<?> clazz, Class<A> annotation) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(annotation)) {
                logger.info("[{}] 存在注解 [{}]", declaredField.getName(), annotation.getName());
                declaredField.setAccessible(true);
                A result = declaredField.getAnnotation(annotation);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}