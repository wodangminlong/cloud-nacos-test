package com.ml.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * response global filter
 *
 * @author Administrator
 * @date 2021/10/23 10:19
 */
@Slf4j
@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange.mutate().response(decorate(exchange)).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }

    @SuppressWarnings(" NullableProblems")
    ServerHttpResponse decorate(ServerWebExchange exchange) {
        String url = exchange.getRequest().getURI().toString();
        log.info("response -> request url: {}", url);
        return new ServerHttpResponseDecorator(exchange.getResponse()) {

            @Override
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {

                String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                HttpHeaders httpHeaders = new HttpHeaders();

                httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);

                ClientResponse clientResponse = ClientResponse
                        .create(Objects.requireNonNull(exchange.getResponse().getStatusCode()))
                        .headers(headers -> headers.putAll(httpHeaders))
                        .body(Flux.from(body)).build();

                Mono<String> modifiedBody = clientResponse.bodyToMono(String.class)
                        .flatMap(originalBody -> {
                            log.info("response content: {}", originalBody);
                            return Mono.just(originalBody);
                        }).switchIfEmpty(Mono.defer(() -> {
                            log.warn("response empty ...");
                            return Mono.empty();
                        }));

                BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInsertEr = BodyInserters.fromPublisher(modifiedBody, String.class);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
                        exchange, exchange.getResponse().getHeaders());
                return bodyInsertEr.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            Flux<DataBuffer> messageBody = outputMessage.getBody();
                            HttpHeaders headers = getDelegate().getHeaders();
                            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                messageBody = messageBody.doOnNext(data -> headers
                                        .setContentLength(data.readableByteCount()));
                            }
                            return getDelegate().writeWith(messageBody);
                        }));
            }

            @Override
            public Mono<Void> writeAndFlushWith(@NonNull Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
    }

}
