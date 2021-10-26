package com.ml.exception;

import com.alibaba.fastjson.JSON;
import com.ml.ApiErrorCode;
import com.ml.ApiResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * exception advice utils
 *
 * @author Administrator
 * @date 2021/10/26 10:37
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ApiResponse handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append(";");
        }
        String result = strBuilder.toString();
        log.error("ExceptionAdviceUtils handle has error: {}", result);
        return ApiResponse.error(ApiErrorCode.PARAMETER_ERROR);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleBindException(BindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                         @NonNull WebRequest request) {
        return new ResponseEntity<>(buildMessages(ex.getBindingResult()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status, @NonNull WebRequest request) {
        return new ResponseEntity<>(buildMessages(ex.getBindingResult()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                       @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                       @NonNull WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, @NonNull HttpHeaders headers,
                                                        @NonNull HttpStatus status, @NonNull WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private String buildMessages(BindingResult result) {
        StringBuilder resultBuilder = new StringBuilder();
        List<ObjectError> errors = result.getAllErrors();
        if (!CollectionUtils.isEmpty(errors)) {
            for (ObjectError error : errors) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    String fieldErrMsg = fieldError.getDefaultMessage();
                    resultBuilder.append(fieldErrMsg);
                    break;
                }
            }
        }
        log.error("buildMessages resultBuilder: {}", resultBuilder);
        return JSON.toJSONString(ApiResponse.error(ApiErrorCode.PARAMETER_ERROR));
    }

}
