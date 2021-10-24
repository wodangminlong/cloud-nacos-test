package com.ml.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * request global filter
 *
 * @author Administrator
 * @date 2021/10/24 00:22
 */
@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        request.getBody();
        String url = request.getURI().toString();
        log.info("request url: {}", url);
        HttpHeaders httpHeaders = request.getHeaders();
        log.info("request http headers: {}", JSON.toJSONString(httpHeaders));
        MultiValueMap<String, String> queryParamMap = request.getQueryParams();
        log.info("request query params: {}", paramMapToString(queryParamMap));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }

    /**
     * param map to string
     *
     * @param paramMap paramMap
     * @return string
     */
    public static String paramMapToString(MultiValueMap<String, String> paramMap) {
        String prefix = "&";
        String result = paramMap.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(prefix));
        if (result.startsWith(prefix) && result.length() > 1) {
            result = result.substring(1);
        }
        return result;
    }

}
