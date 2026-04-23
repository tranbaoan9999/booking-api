package com.booking.common.exception;

import com.booking.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // BUSINESS ERROR
    @ExceptionHandler(AppException.class)
    public ResponseEntity<BaseResponse<?>> handleAppException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(BaseResponse.error(ex.getMessage()));
    }

    // SYSTEM ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(BaseResponse.error("Internal Server Error"));
    }
}