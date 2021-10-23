package com.ml.exception;

import com.ml.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * global exception handling
 *
 * @author Administrator
 * @date 2021/10/23 10:28
 */
@Order(-1)
@Slf4j
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    public JsonExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    /**
     * get exception properties
     *
     * @param request request
     * @param options options
     * @return map
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        int code = ApiErrorCode.SYSTEM_ERROR.getCode();
        String errMsg = ApiErrorCode.SYSTEM_ERROR.getMsg();
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            code = ApiErrorCode.VOID_REQUEST.getCode();
            errMsg = ApiErrorCode.VOID_REQUEST.getMsg();
        }
        String errorMessage = buildMessage(request, error);
        log.error("response status:{}, errorMessage:{}", code, errorMessage);
        return response(code, errMsg);
    }

    /**
     * use json to handling exception
     *
     * @param errorAttributes errorAttributes
     * @return RouterFunction
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    @Nonnull
    protected Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {

        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorPropertiesMap));
    }

    /**
     * get httpStatus by code
     *
     * @param errorAttributes errorAttributes
     * @return int
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (int) errorAttributes.get("code");
    }

    /**
     * build exception message
     *
     * @param request request
     * @param ex      ex
     * @return string
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (ex != null) {
            message.append(": ");
            message.append(ex.getMessage());
        }
        return message.toString();
    }

    /**
     * build response json data
     *
     * @param status       status
     * @param errorMessage errorMessage
     * @return map
     */
    public static Map<String, Object> response(int status, String errorMessage) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("code", status);
        map.put("msg", errorMessage);
        return map;
    }
}
