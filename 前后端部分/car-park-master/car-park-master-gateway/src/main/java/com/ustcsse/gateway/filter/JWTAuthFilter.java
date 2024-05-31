package com.ustcsse.gateway.filter;

import com.ustcsse.gateway.feign.AuthFeignService;
import com.ustcsse.gateway.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JWTAuthFilter implements GlobalFilter, Ordered {
    @Autowired
    AuthFeignService authFeignService;
    private static final String LOGIN_URL = "/auth/login";
    private static final String PAY_URL = "/order/pay";
    private static final String NOTIFY_URL = "/order/notify";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        ServerHttpRequest.Builder mutate = serverHttpRequest.mutate();
        String requestUrl = serverHttpRequest.getURI().getPath();
        if(LOGIN_URL.equals(requestUrl) || PAY_URL.equals(requestUrl) || NOTIFY_URL.equals(requestUrl)) {
            return chain.filter(exchange);
        }

        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (!StringUtils.hasText(token)) {
            throw new RuntimeException("用户未登录！");
        }
        //解析token
        R check = authFeignService.check(token);
        if(check.getCode() == 0){
            return chain.filter(exchange);
        }else{
            throw new RuntimeException("Token不合法！");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
