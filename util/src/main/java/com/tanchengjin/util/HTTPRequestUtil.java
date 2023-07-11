package com.tanchengjin.util;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class HTTPRequestUtil {
    private final static Logger logger = LoggerFactory.getLogger(HTTPRequestUtil.class);

    public static String post(String url, String json) {
        return handler(url, json, new Encrypt() {
            @Override
            public String encrypt(String var) {
                return var;
            }
        }, new Decrypt() {
            @Override
            public String decrypt(String var) {
                return var;
            }
        });
    }

    /**
     * @param url  发起请求的url
     * @param json JSON格式的字符串
     * @return
     */
    public static String handler(String url, String json, Encrypt dataEncrypt, Decrypt responseDecrypt) {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpClient = HttpClients.createDefault();
            try {
                json = dataEncrypt.encrypt(json);
                BasicResponseHandler handler = new BasicResponseHandler();
                StringEntity entity = new StringEntity(json, "UTF-8");//解决中文乱码问题
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
                result = httpClient.execute(httpPost, handler);
                return responseDecrypt.decrypt(result);
            } catch (Exception e) {
                logger.info("请求报错>>> [{}]" + e.getMessage());
            } finally {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    logger.info("请求关闭报错>>> [{}]" + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.info("http connect create failed [{}]", e.getMessage());
        }
        return result;
    }

    /**
     * 将map转换为url格式的参数
     */
    public static String mapToUrlParams(Map<String, String> source) {
        Iterator<String> it = source.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            String value = source.get(key);
            if (value == null || value.length() == 0) {
                continue;
            }
            try {
                // URL 编码
                value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // do nothing
            }
            paramStr.append("&").append(key).append("=").append(value);
        }
        // 去除第一个&
        return paramStr.substring(1);
    }

    public interface Encrypt {
        default String encrypt(String var) {
            return var;
        }
    }

    public interface Decrypt {
        default String decrypt(String var) {
            return var;
        }
    }
}
