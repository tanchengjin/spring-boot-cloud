package com.tanchengjin.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class AuthUtil {
    public static User getUser(HttpServletRequest request) {
        Map userOfHeader = getUserOfHeader(request);
        User user = new User();
        if (userOfHeader.containsKey("userId")) {
            user.setId(userOfHeader.get("userId").toString());
        }
        if (userOfHeader.containsKey("user_name")) {
            user.setUsername(userOfHeader.get("user_name").toString());
        }
        return user;
    }

    public static Map getUserOfHeader(HttpServletRequest request) {
        String user = getHeaderUserRaw(request);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(user, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHeaderUserRaw(HttpServletRequest request) {
        return request.getHeader(Constants.HeaderUserKey);
    }
}
