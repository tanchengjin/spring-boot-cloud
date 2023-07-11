//package com.tanchengjin.app.annotations.dict;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tanchengjin.app.modules.test.vo.TestVO;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.beanutils.PropertyUtilsBean;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cglib.beans.BeanGenerator;
//import org.springframework.cglib.beans.BeanMap;
//
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author: Yang
// * @date: 2020/11/7 14:47
// * @description:
// */
//@Slf4j
//public final class PropertyAppender {
//    private final static Logger logger = LoggerFactory.getLogger(PropertyAppender.class);
//
//    private static final class DynamicBean {
//
//        private Object target;
//
//        private BeanMap beanMap;
//
//        private DynamicBean(Class superclass, Map<String, Class> propertyMap) {
//            this.target = generateBean(superclass, propertyMap);
//            this.beanMap = BeanMap.create(this.target);
//        }
//
//        private void setValue(String property, Object value) {
//            beanMap.put(property, value);
//        }
//
//        private Object getValue(String property) {
//            return beanMap.get(property);
//        }
//
//        private Object getTarget() {
//            return this.target;
//        }
//
//        /**
//         * 根据属性生成对象
//         */
//        private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
//            BeanGenerator generator = new BeanGenerator();
//            if (null != superclass) {
//                generator.setSuperclass(superclass);
//            }
//            BeanGenerator.addProperties(generator, propertyMap);
//            return generator.create();
//        }
//    }
//
//    public static Object generate(Object dest, Map<String, Object> newValueMap) throws InvocationTargetException, IllegalAccessException {
//        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
//
//        //1.获取原对象的字段数组
//        PropertyDescriptor[] descriptorArr = propertyUtilsBean.getPropertyDescriptors(dest);
//
//        //2.遍历原对象的字段数组，并将其封装到Map
//        Map<String, Class> oldKeyMap = new HashMap<>(4);
//        for (PropertyDescriptor it : descriptorArr) {
//            if (!"class".equalsIgnoreCase(it.getName())) {
//                oldKeyMap.put(it.getName(), it.getPropertyType());
//                newValueMap.put(it.getName(), it.getReadMethod().invoke(dest));
//            }
//        }
//
//        //3.将扩展字段Map合并到原字段Map中
//        newValueMap.forEach((k, v) -> oldKeyMap.put(k, v == null ? null : v.getClass()));
//
//        //4.根据新的字段组合生成子类对象
//        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), oldKeyMap);
//
//        //5.放回合并后的属性集合
//        newValueMap.forEach((k, v) -> {
//            try {
//                dynamicBean.setValue(k, v);
//            } catch (Exception e) {
////                log.error("动态添加字段【值】出错", e);
//            }
//        });
//        return dynamicBean.getTarget();
//    }
//
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//
//    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
//        TestVO testVO = new TestVO();
//        testVO.setName("Daisy");
////        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(user));
//
//        System.out.println("=====================================");
//
//        Map<String, Object> propertiesMap = new HashMap<>(1);
//        propertiesMap.put("age", 18);
//
//        Object obj = PropertyAppender.generate(testVO, propertiesMap);
//
//        System.err.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
//    }
//}