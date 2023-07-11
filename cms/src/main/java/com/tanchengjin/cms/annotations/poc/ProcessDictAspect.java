package com.tanchengjin.cms.annotations.poc;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/

import com.tanchengjin.cms.annotations.dict.Dict;
import com.tanchengjin.util.ServerResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 实现自定义注解切面
 */
@Aspect
@Component
public class ProcessDictAspect {

    private final static Logger logger = LoggerFactory.getLogger(ProcessDictAspect.class);

    @AfterReturning(value = "@annotation(com.tanchengjin.cms.annotations.poc.ProcessDict)", returning = "result")
    public Object aftreReturning(JoinPoint joinPoint,
                                 Object result) throws IllegalAccessException {
        logger.info(joinPoint.toString());
        logger.info("<===================Aspect======================>");
        logger.info(result.toString());

        if (result == null) {
            return null;
        }
        if (result instanceof ServerResponse) {
            Object body = ((ServerResponse<?>) result).getData();
            if (body == null) {
                return null;
            }
            if (body instanceof Collection) {
                for (Object obj : (Collection<?>) body) {
                    //处理
                    processObj(obj);
                }
            } else {
                //处理
                processObj(body);
            }
        } else if (result instanceof Collection) {
            //处理
            for (Object obj : (Collection<?>) result) {
                //处理
                processObj(obj);
            }
        } else {
            processObj(result);

        }
        return result;
    }

    private void processObj(Object obj) throws IllegalAccessException {
        //取出Obj所有 Field，以及父类 Field
        List<Field> fieldList = new ArrayList<>();
        Class<?> tempClass = obj.getClass();
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);

        ArrayList<String> fieldTmp = new ArrayList<>();

        //遍历Field,查找添加 @SexValue 注解的字段 并 做翻译
        for (Field field : fields) {
            if (field.isAnnotationPresent(Dict.class)) {
                fieldTmp.add(field.getName());
                field.setAccessible(true);
                String fieldValue = String.valueOf(field.get(obj));
                field.set(obj, "112233");
//				logger.info("fieldValue:{}",fieldValue);
//				if ("M".equals(fieldValue)){
//					field.set(obj,"男");
//				}else {
//					field.set(obj,"女");
//				}
            }
        }

        Field[] fieldsNew = new Field[fieldList.size() + fieldTmp.size()];

        fieldList.toArray(fieldsNew);
    }
}
