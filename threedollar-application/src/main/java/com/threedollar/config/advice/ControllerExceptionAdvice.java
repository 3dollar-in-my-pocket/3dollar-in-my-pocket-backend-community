package com.threedollar.config.advice;

import com.threedollar.common.dto.response.ApiResponse;
import com.threedollar.common.exception.BaseException;
import com.threedollar.common.exception.ErrorCode;
import com.threedollar.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.threedollar.common.exception.ErrorCode.E400_INVALID;
import static com.threedollar.common.exception.ErrorCode.E404_NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    private ApiResponse<Object> handleBadRequest(BindException e) {
        List<String> reasons = e.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.toList());
        log.warn(StringUtils.join(reasons, ", "), e);
        return ApiResponse.error(E400_INVALID, reasons);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<Object> handleNotFoundRequest(BindException e) {
        List<String> reasons = e.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.toList());
        log.warn(StringUtils.join(reasons, ", "), e);
        return ApiResponse.error(E404_NOT_FOUND, reasons);
    }


    @ExceptionHandler(BaseException.class)
    private ResponseEntity<ApiResponse<Object>> handleBaseException(BaseException e) {
        log.error(e.getErrorCode().getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ApiResponse.error(e.getErrorCode()));
    }

    @ExceptionHandler(Throwable.class)
    private ResponseEntity<ApiResponse<Object>> handleThrowable(Throwable throwable) {
        if (throwable.getCause() instanceof BaseException baseException) {
            log.error(throwable.getMessage(), throwable);
            return ResponseEntity.status(baseException.getErrorCode().getStatus())
                .body(ApiResponse.error(baseException.getErrorCode()));
        }

        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.internalServerError()
            .body(ApiResponse.error(ErrorCode.E500_INTERNAL_SERVER));
    }

}
