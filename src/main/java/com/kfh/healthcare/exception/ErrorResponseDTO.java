package com.kfh.healthcare.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseDTO(
        Integer status,
        String message,
        LocalDateTime timestamp,
        Map<String, String> errors
) {
    public ErrorResponseDTO(Integer status, String message) {
        this(status, message, LocalDateTime.now(), null);
    }
}
