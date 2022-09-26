package com.tanchengjin.test.util.throttle;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ThrottleInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Throttle methodAnnotation = hm.getMethodAnnotation(Throttle.class);
            if (methodAnnotation == null) {
                return true;
            }

            long second = methodAnnotation.second();
            int count = methodAnnotation.count();
            String requestURI = request.getRequestURI();

            Integer ct = ConcurrentUtil.increment(requestURI, second * 1000L);
            if (ct != null && ct > count) {
                //超出所限制次数
                response(response);
                return false;
            }
//            if (ct == null || ct < count) {
//                //第一次访问或未超过次数访问
//
//            } else {
//                //超出所限制次数
//                response(response);
//                return false;
//            }

        }
        return true;
    }

    private void response(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UFT-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String json = "{\"code\":\"100\"}";
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
