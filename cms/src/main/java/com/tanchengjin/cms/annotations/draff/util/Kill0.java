package com.tanchengjin.cms.annotations.draff.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Kill0 {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {

        User0 user = new User0();
        user.setName("Daisy");
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(user));

        System.out.println("=====================================");

        Map<String, Object> propertiesMap = new HashMap<>(1);
        propertiesMap.put("age", 18);

        Object obj = PropertyAppender.generate(user, propertiesMap);
        System.err.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }

}