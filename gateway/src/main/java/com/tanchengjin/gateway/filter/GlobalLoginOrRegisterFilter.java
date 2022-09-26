//package com.tanchengjin.gateway.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.tanchengjin.gateway.constant.GatewayConstant;
//import com.tanchengjin.gateway.util.ResponseUtil;
//import com.tanchengjin.gateway.util.ServerResponse;
//import com.tanchengjin.gateway.vo.LoginOrRegisterVO;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Component
//public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {
//    private final static Logger logger = LoggerFactory.getLogger(GlobalLoginOrRegisterFilter.class);
//
//    /**
//     * 注册中心客户端，可以从注册中心获取服务实例信息
//     */
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
//    @Autowired
//    private RestTemplate restTemplate;
//
//    /**
//     * 登录、注册、鉴权
//     * 1. 如果是登录或注册，则去授权中心拿到token并返回给客户端
//     * 2. 如果是访问其他的服务，则鉴权，没有权限返回401
//     *
//     * @param exchange
//     * @param chain
//     * @return
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        //是否为登录请求
//        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
//            ServerResponse<JwtTokenVO> serverResponse = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT);
//            //header中不能设置null
////            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, null == token ? "null" : token);
////            response.setStatusCode(HttpStatus.OK);
////            JwtTokenVO jwtTokenVO = new JwtTokenVO();
////            jwtTokenVO.setToken(token);
//            DataBuffer responseBuffer = ResponseUtil.getResponseBuffer(response, serverResponse);
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            return response.writeWith(Mono.just(responseBuffer));
////            return response.setComplete();
//        }
//
//
//        //如果是注册请求
//        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
//            //创建一个用户并返回token
//            ServerResponse<JwtTokenVO> serverResponse = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT);
////            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, null == token ? "null" : token);
////            response.setStatusCode(HttpStatus.OK);
////            return response.setComplete();
//            DataBuffer responseBuffer = ResponseUtil.getResponseBuffer(response, serverResponse);
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            return response.writeWith(Mono.just(responseBuffer));
//        }
//
//        //如果不是登录或者注册,则进行校验,校验token
//        HttpHeaders headers = request.getHeaders();
//        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
//        LoginUserInfo loginUserInfo = null;
//        try {
////            Claims claims = JwtUtil.parseToken(token);
//            loginUserInfo = new LoginUserInfo();
////            loginUserInfo.setId(Long.valueOf(claims.getId()));
//            Map<String, Claim> stringClaimMap = JWTUtil.verifyTokenAndGetClaim(token);
//            if (stringClaimMap.containsKey("userId") && stringClaimMap.containsKey("name")) {
//                Long userId = stringClaimMap.get("userId").asLong();
//                String name = stringClaimMap.get("name").asString();
//                loginUserInfo.setUsername(name);
//            } else {
//                DataBuffer responseBuffer = ResponseUtil.getResponseBuffer(response, ServerResponse.responseWithFailureMessage("非法令牌!"));
//                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//                return response.writeWith(Mono.just(responseBuffer));
//            }
////            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
//        } catch (Exception e) {
//            logger.error("parse user info from token error: [{}]", e.getMessage(), e);
//            DataBuffer responseBuffer = ResponseUtil.getResponseBuffer(response, ServerResponse.responseWithFailureMessage(e.getMessage()));
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            return response.writeWith(Mono.just(responseBuffer));
//        }
//
//        //无法获取到用户信息，返回401
//        if (null == loginUserInfo) {
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            DataBuffer responseBuffer = ResponseUtil.getResponseBuffer(response, ServerResponse.responseWithFailureMessage("认证失败"));
//            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//            //            return response.setComplete();
//            return response.writeWith(Mono.just(responseBuffer));
//        }
//
//        //校验通过，放行
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return HIGHEST_PRECEDENCE + 2;
//    }
//
//
//    /**
//     * 通过授权中心获取token
//     *
//     * @param request
//     * @param uriFormat
//     * @return
//     */
//    private ServerResponse<JwtTokenVO> getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat) {
//        //负载均衡功能实现
//        //选择授权中心的服务
//        ServiceInstance serviceInstance = loadBalancerClient.choose(
//                CommonConstant.AUTHORITY_CENTER_SERVICE_ID
//        );
//        logger.info("Nacos Client Info: [{}],[{}],[{}]", serviceInstance.getServiceId(), serviceInstance.getInstanceId(), JSON.toJSONString(serviceInstance.getMetadata()));
//        String requestUrl = String.format(uriFormat, serviceInstance.getHost(), serviceInstance.getPort());
//        LoginOrRegisterVO requestBody = JSON.parseObject(parseBodyFromRequest(request), LoginOrRegisterVO.class);
//        logger.info("login request url and body: [{}],[{}]", requestUrl, JSON.toJSONString(requestBody));
//
//        //向注册中心发送请求
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ServerResponse serverResponse = restTemplate.postForObject(requestUrl, new HttpEntity<>(JSON.toJSONString(requestBody), headers), ServerResponse.class);
//        return serverResponse;
//    }
//
//    /**
//     * 从Post请求中拿到请求数据
//     *
//     * @param request
//     * @return
//     */
//    private String parseBodyFromRequest(ServerHttpRequest request) {
//        //获取请求体
//        Flux<DataBuffer> body = request.getBody();
//        AtomicReference<String> bodyRef = new AtomicReference<>();
//
//        body.subscribe(buffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//            //释放到缓冲区数据，否者会出现内存泄露
//            DataBufferUtils.release(buffer);
//
//            bodyRef.set(charBuffer.toString());
//        });
//        //获取request body
//        return bodyRef.get();
//    }
//}
