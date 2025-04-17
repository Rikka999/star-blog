package com.mc.starblog.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Result<T> {

    private int code;
    private String message;
    private T data;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime timestamp; // 响应时间

    private Result(int code, String message, T data ) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static  Result<Void> success() {
        return new Result<>(200, "success", null);
    }

    public static  Result<Void> success(String message) {
        return new Result<>(200, message, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

}
