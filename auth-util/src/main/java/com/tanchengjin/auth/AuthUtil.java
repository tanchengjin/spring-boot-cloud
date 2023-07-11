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
    public static Userinfo getUser(HttpServletRequest request) {
        Map userOfHeader = getUserOfHeader(request);
        Userinfo user = new Userinfo();
        if (userOfHeader.containsKey("user_id")) {
            user.setId(userOfHeader.get("user_id").toString());
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
