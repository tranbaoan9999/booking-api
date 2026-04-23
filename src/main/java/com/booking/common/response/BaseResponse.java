package com.booking.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private boolean success;
    private T data;
    private String message;
    private LocalDateTime timestamp;

    public BaseResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // SUCCESS
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, data, null);
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(true, data, message);
    }

    // ERROR
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(false, null, message);
    }

    // getters
    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}