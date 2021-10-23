package com.ml.filter;

import com.alibaba.fastjson.JSON;
import com.ml.ApiErrorCode;
import com.ml.ApiResponse;
import com.ml.util.IpUtils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * use bucket to limit requests
 *
 * @author Administrator
 * @date 2021/10/23 10:17
 */
@Slf4j
@Component
public class IpLimitFilter implements GlobalFilter, Ordered {

    private static final Map<String, Bucket> LOCAL_CACHE = new ConcurrentHashMap<>();

    /**
     * bucket init count
     */
    public static final long CAPACITY = 3;

    /**
     * time interval for replenishing bucket
     * how many seconds to add once
     */
    public static final long SECONDS = 1;

    /**
     * the number of tokens added each time
     */
    public static final long REFILL_TOKENS = 3;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String ip = IpUtils.getIpAddr(request);
        Bucket bucket = LOCAL_CACHE.computeIfAbsent(ip, k -> createNewBucket());
        String uri = request.getURI().getPath();
        log.info("request ip:{} , uri: {}, number of tokens available in the bucket:{} ", ip, uri, bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        }
        // content type utf-8
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory()
                .wrap(JSON.toJSONBytes(ApiResponse.error(ApiErrorCode.REQUESTS_ARE_TOO_FREQUENT)))));
    }

    @Override
    public int getOrder() {
        return -1;
    }


    private Bucket createNewBucket() {
        Duration refillDuration = Duration.ofSeconds(SECONDS);
        Refill refill = Refill.greedy(REFILL_TOKENS, refillDuration);
        Bandwidth limit = Bandwidth.classic(CAPACITY, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

}
