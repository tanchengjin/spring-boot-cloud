package com.tanchengjin.oauth2.gateway.conf;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2020/6/19.
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationManager.class);
    /**
     * 此处保存的是资源对应的权限，可以从数据库中获取
     */
    private static final Map<String, String> AUTH_MAP = Maps.newConcurrentMap();
    /**
     * 白名单列表
     */
    private static final String[] whiteArray = {
            "/app-test/user", "/app-test/test"
    };

    @PostConstruct
    public void initAuthMap() {
        AUTH_MAP.put("/user/findAllUsers", "user.userInfo");
        AUTH_MAP.put("/user/addUser", "ROLE_ADMIN");
        AUTH_MAP.put("/app-test/test", "ROLE_ADMIN");
        AUTH_MAP.put("/app-test/user", "ROLE_ADMIN");
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 带通配符的可以使用这个进行匹配
        PathMatcher pathMatcher = new AntPathMatcher();
        String authorities = AUTH_MAP.get(path);
        logger.info("访问路径:[{}],所需要的权限是:[{}]", path, authorities);

        // option 请求，全部放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        //白名单放行
        List<String> whiteList = Arrays.asList(whiteArray);
        if (whiteList.contains(path)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 不在权限范围内的url，全部拒绝
//        if (!StringUtils.hasText(authorities)) {
//            return Mono.just(new AuthorizationDecision(false));
//        }

        //从Redis中获取当前路径可访问角色列表
//        URI uri = authorizationContext.getExchange().getRequest().getURI();
//        Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
//        List<String> authorities = new ArrayList<>();
//        List<String> authorities = Convert.toList(String.class,obj);
//        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authorities::contains)
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
        return mono
                .filter(Authentication::isAuthenticated)
                .filter(a -> a instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .doOnNext(token -> {
                    logger.info("token attribute [{}]", token.getTokenAttributes());
//                    System.out.println(token.getToken().getHeaders());
                })
                .flatMapIterable(AbstractAuthenticationToken::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
//                    Objects.equals(authority, authorities)
                    return true;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}