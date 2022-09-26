package com.tanchengjin.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.nio.charset.StandardCharsets;

public class ResponseUtil {

    public static DataBuffer getResponseBuffer(ServerHttpResponse response, ServerResponse serverResponse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "-1");
        //将json对象转换为字节数组
//        new JSONObject()
        String s = JSONObject.toJSONString(serverResponse, SerializerFeature.WriteNullNumberAsZero);
        byte[] bits = s.getBytes(StandardCharsets.UTF_8);
//        byte[] bits = jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
        //将字节转换为dataBuffer
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        return buffer;
    }
}
