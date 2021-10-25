package com.ml.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ml.ApiErrorCode;
import com.ml.ApiResponse;
import com.ml.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * request global filter
 *
 * @author Administrator
 * @date 2021/10/23 10:18
 */
@Slf4j
@Component
@RefreshScope
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        request.getBody();
        String url = request.getURI().toString();
        log.info("request url: {}", url);
        HttpHeaders httpHeaders = request.getHeaders();
        log.info("request http headers: {}", JSON.toJSONString(httpHeaders));
        String appidHeader = httpHeaders.getFirst(Constant.HTTP_HEADER_APPID);
        if (StringUtils.isBlank(appidHeader) || !Constant.TEST_APPID.equals(appidHeader)) {
            return forbidden(exchange);
        }
        String authorizationHeader = httpHeaders.getFirst(Constant.HTTP_HEADER_SIGN);
        if (StringUtils.isBlank(authorizationHeader)) {
            return forbidden(exchange);
        }
        MultiValueMap<String, String> queryParamMap = request.getQueryParams();
        if (!CollectionUtils.isEmpty(queryParamMap)) {
            String requestQueryParamsStr = paramMapToString(queryParamMap, Constant.TEST_APP_SECRET);
            log.info("request query params: {}, encryptStr: {}", JSON.toJSONString(queryParamMap), requestQueryParamsStr);
            if (!requestQueryParamsStr.equals(authorizationHeader)) {
                return forbidden(exchange);
            }
        }
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
     * @param signKey signKey
     * @return string
     */
    public static String paramMapToString(MultiValueMap<String, String> paramMap, String signKey) {
        String prefix = "&";
        String result = paramMap.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(prefix));
        result += prefix + signKey;
        log.info("before md5 str: {}", result);
        return DigestUtils.md5DigestAsHex(result.getBytes(StandardCharsets.UTF_8));
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ApiResponse apiResponse = new ApiResponse(ApiErrorCode.FORBIDDEN.getCode(), ApiErrorCode.FORBIDDEN.getMsg());
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(JSON.toJSONBytes(apiResponse))));
    }

}
