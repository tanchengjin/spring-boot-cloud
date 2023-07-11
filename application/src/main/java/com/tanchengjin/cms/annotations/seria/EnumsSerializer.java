package com.tanchengjin.cms.annotations.seria;

import com.alibaba.fastjson.serializer.*;
import com.tanchengjin.cms.annotations.dict.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;

/**
 * 功能描述 : 枚举自定义序列化
 *
 * @author ziyear 2021-12-18 19:23
 */
public class EnumsSerializer implements ObjectSerializer {
    private final static Logger logger = LoggerFactory.getLogger(EnumsSerializer.class);
    private static final String FIELD_KEY_EXT = "Name";
    private static final String BEAN_START = "{";
    private static final String BEAN_END = "}";
    private static final String EXT_FIELD_FORMAT = ",\"{0}\": \"{1}\"";

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.write(object);
            return;
        }
        JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(object.getClass()) {
            @Override
            public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
                super.write(serializer, object, fieldName, fieldType, features, true);
            }
        };
        SerializeWriter out = serializer.getWriter();
        out.write(BEAN_START);
        javaBeanSerializer.write(serializer, object, fieldName, fieldType, features);

        try {
            Set<String> fieldNames = javaBeanSerializer.getFieldNames(object);
            for (String name : fieldNames) {
                FieldSerializer fieldSerializer = javaBeanSerializer.getFieldSerializer(name);
                Field field = fieldSerializer.fieldInfo.field;
                if (field != null) {
                    final Dict annotation = AnnotationUtils.findAnnotation(field, Dict.class);
                    if (annotation != null) {
                        Class<?> value = annotation.value();
                        String methodName = annotation.method();
                        Method method;
                        try {
                            method = value.getMethod(methodName, String.class);
                        } catch (Exception e) {
                            logger.error("方法不存在！{}#{}", value, methodName);
                            continue;
                        }
                        Object fieldValue = javaBeanSerializer.getFieldValue(object, name);
                        Object msg = method.invoke(null, Objects.toString(fieldValue));
                        writeExtField(out, name, msg);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("序列化出现错误！", e);
        } finally {
            out.write(BEAN_END);
        }
    }

    private void writeExtField(SerializeWriter out, String key, Object value) {
        String format = MessageFormat.format(EXT_FIELD_FORMAT, key + FIELD_KEY_EXT, value);
        out.write(format);
    }
}