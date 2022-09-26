//package com.tanchengjin.gateway.filter;
//
//import com.tanchengjin.gateway.config.GatewayConfig;
//import com.tanchengjin.gateway.constant.GatewayConstant;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
///**
// * 缓存http Body
// * String WebFlux (完全异步)
// */
//@Component
//public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
//    private final static Logger logger = LoggerFactory.getLogger(GlobalCacheRequestBodyFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //判断是注册还是登录
//
//        boolean isLoginOrRegister = exchange.getRequest().getURI().getPath().contains(GatewayConstant.LOGIN_URI) || exchange.getRequest().getURI().getPath().contains(GatewayConstant.REGISTER_URI);
//        //不满足特定条件继续往下走其他的过滤器
//        if (null == exchange.getRequest().getHeaders().getContentType() || !isLoginOrRegister) {
//            return chain.filter(exchange);
//        }
//
//        //缓存逻辑
//
//        //DataBufferUtils.join 拿到请求中的数据 --> DataBuffer
//        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
//            //确保数据缓冲区不被释放 ,必须要DataBufferUtils.retain
//            DataBufferUtils.retain(dataBuffer);
//
//            //defer、just 都是去创建数据源，得到当前数据的副本
//            Flux<DataBuffer> cacheFlux = Flux.defer(() ->
//                    Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount()))
//            );
//            //重新包装 ServerHttpRequest,重写getBody方法，能够返回数据
//            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
//                    exchange.getRequest()
//            ) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return cacheFlux;
//                }
//            };
//            //将包装之后的ServerHttpRequest向下继续传递
//            return chain.filter(exchange.mutate().request(mutatedRequest).build());
//        });
//    }
//
//    @Override
//    public int getOrder() {
//        return HIGHEST_PRECEDENCE + 1;
//    }
//}
