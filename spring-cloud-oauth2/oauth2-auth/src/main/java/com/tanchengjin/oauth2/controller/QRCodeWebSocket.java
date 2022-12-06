package com.tanchengjin.oauth2.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tanchengjin.oauth2.modules.oauth.enumeration.QRCodeStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@ServerEndpoint("/auth/qrcode-info/{sid}")
@Component
@Service
public class QRCodeWebSocket {
    private final static Logger logger = LoggerFactory.getLogger(QRCodeWebSocket.class);

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        QRCodeWebSocket.redisTemplate = redisTemplate;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<QRCodeWebSocket> webSocketSet = new CopyOnWriteArraySet<QRCodeWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        this.sid = sid;
        onMessage("123", session);
        sendQRCodeState();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自窗口 [{}]  的信息: [{}]", sid, message);
        //群发消息
//        for (QRCodeWebSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        while (1 == 1) {
            try {
                Thread.sleep(2000);
                sendQRCodeState();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendQRCodeState() {
        logger.info("uuid [{}]", sid);
        //获得当前二维码的状态
        int code = QRCodeStateEnum.NONEXISTENT.getCode();
        Integer s = (Integer) redisTemplate.opsForValue().get(sid + "_state");
        if (!StringUtils.isEmpty(s)) {
            code = s;
        }
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("status", code);
        stringObjectHashMap.put("description", QRCodeStateEnum.getTextByCode(code));

        try {
            session.getBasicRemote().sendText(new JsonMapper().writeValueAsString(stringObjectHashMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        logger.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (QRCodeWebSocket item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        QRCodeWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        QRCodeWebSocket.onlineCount--;
    }
}
