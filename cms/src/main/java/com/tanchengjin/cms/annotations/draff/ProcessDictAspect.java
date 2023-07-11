//package com.tanchengjin.app.annotations.dict;
//
///**
// * @Author TanChengjin
// * @Email 18865477815@163.com
// * @Since V1.0.0
// **/
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tanchengjin.app.annotations.dict.util.PropertyAppender;
//import com.tanchengjin.util.ServerResponse;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.util.*;
//
//
///**
// * 实现自定义注解切面
// */
//@Aspect
//@Component
//public class ProcessDictAspect {
//
//	private final static Logger logger = LoggerFactory.getLogger(ProcessDictAspect.class);
//
//	@AfterReturning(value = "@annotation(ProcessDict)",returning = "result")
//	public Object aftreReturning(JoinPoint joinPoint,
//								 Object result) throws IllegalAccessException {
//		logger.info(joinPoint.toString());
//		logger.info("<===================Aspect======================>");
//		logger.info(result.toString());
//
//		if (result == null){
//			return null;
//		}
//		if (result instanceof ServerResponse){
//			Object body = ((ServerResponse<?>) result).getData();
//			if (body == null){
//				return null;
//			}
//			if (body instanceof Collection ){
//				for (Object obj : (Collection<?>) body){
//					//处理
//					processObj(obj);
//				}
//			}else {
//				//处理
//				processObj(body);
//			}
//		}else if (result instanceof Collection){
//			//处理
//			for (Object obj : (Collection<?>) result){
//				//处理
//				processObj(obj);
//			}
//		}else {
//			processObj(result);
//
//		}
//		return result;
//	}
//
//	private static final ObjectMapper MAPPER = new ObjectMapper();
//
//	private void processObj(Object obj) throws IllegalAccessException {
//
//		HashMap<String, Object> stringObjectHashMap = new HashMap<>(1);
//		stringObjectHashMap.put("power",1);
//		Object generate = null;
//		try {
//			generate = PropertyAppender.generate(obj, stringObjectHashMap);
//			System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(generate));
//		} catch (InvocationTargetException | JsonProcessingException e) {
//			throw new RuntimeException(e);
//		}
//
//
//		//取出Obj所有 Field，以及父类 Field
//		List<Field> fieldList = new ArrayList<>();
//		obj = generate;
//		Class<?> tempClass  = obj.getClass();
//		while (tempClass != null){
//			Field[] declaredFields = tempClass.getDeclaredFields();
//			List<Field> list = Arrays.asList(declaredFields);
//			fieldList.addAll(list);
//			tempClass = tempClass.getSuperclass();
//		}
//		Field[] fields = new Field[fieldList.size()];
//		fieldList.toArray(fields);
//
//		//遍历Field,查找添加 @SexValue 注解的字段 并 做翻译
//		for (Field field : fields){
////			if (field.isAnnotationPresent(Dict.class)){
////				field.setAccessible(true);
////				String fieldValue = String.valueOf(field.get(obj));
////				field.set(obj,"112233");
//////				logger.info("fieldValue:{}",fieldValue);
//////				if ("M".equals(fieldValue)){
//////					field.set(obj,"男");
//////				}else {
//////					field.set(obj,"女");
//////				}
////			}
//		}
//	}
//}
