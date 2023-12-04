package com.tanchengjin.minio.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
//@Component
public class ParamsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        String[] excludeUrlList = {"/news", "/power"};
        if (!isContains(excludeUrlList, requestURI)) {
            try {
                String paramData = httpServletRequest.getParameter("data");
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                String body = IOUtils.toString(httpServletRequest.getInputStream(), String.valueOf(StandardCharsets.UTF_8));
                ObjectMapper objectMapper = new ObjectMapper();
                throw new ServletException("error");
            } catch (Exception exception) {
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setStatus(500);
                PrintWriter writer = httpServletResponse.getWriter();
                writer.print("{\"code:500\",\"msg:\"ILLEGAL EXCEPTION\"\"}");
                writer.flush();
                writer.close();
            }
        } else {
            super.doFilter(httpServletRequest, httpServletResponse, filterChain);
        }
    }

    private boolean isContains(String[] urlList, String url) {
        for (String s : urlList) {
            if (s.contains(url)) return true;
        }
        return false;
    }
}
