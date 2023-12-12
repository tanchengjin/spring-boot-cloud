package com.tanchengjin.websocket.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用一个简单的消息代理，用于消息的路由
        config.enableSimpleBroker("/topic");
        // 设置应用程序的前缀，用于客户端发送消息到控制器
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个端点，客户端将使用这个端点连接WebSocket
        registry.addEndpoint("/chat").withSockJS();
        registry.addEndpoint("/chat-debug").setAllowedOrigins("*");
        registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    }
}
