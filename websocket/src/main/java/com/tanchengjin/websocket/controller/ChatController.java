package com.tanchengjin.websocket.controller;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
public class ChatController {
    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

//    private final SimpleMessageTemplate simpMessagingTemplate;
    /**
     * 订阅⼴播，服务器主动推给连接的客户端
     * 通过Http请求的⽅式触发订阅操作
     */
    @RequestMapping("/subscribeTopic")
    public Object subscribeTopicByHttp() throws InterruptedException {
        System.out.println("进入方法");
        while (true) {
            // 可以灵活设置成通道地址，实现发布订阅的功能
            val channel = "/topic/subscribeTopic";
//            simpMessagingTemplate.convertAndSend(channel, Instant.now());
            Thread.sleep(10 * 1000);
        }
    }

    /**
     * 订阅⼴播，服务器主动推给连接的客户端
     * 通过Websocket的subscribe操作触发订阅操作
     */
    @SubscribeMapping("/subscribeTopic")
    public Object subscribeTopicByWebSocket() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 服务端接收客户端发送的消息，类似OnMessage⽅法
     */
    @MessageMapping("/sendToServer")
    public void handleMessage(String message) {
        logger.info("message: {}", message);
    }

    /**
     * 将客户端发送的消息⼴播出去
     */
    @MessageMapping("/sendToTopic")
    @SendTo("/topic/subscribeTopic")
    public Object sendToTopic(String message) {
        return message;
    }
}