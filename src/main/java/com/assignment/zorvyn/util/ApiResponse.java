package com.assignment.zorvyn.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String message;
    private boolean error;
    private T content;

    public static <T> ApiResponse<T> success(String message, T content) {
        return new ApiResponse<>(message, false, content);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, true, null);
    }
}