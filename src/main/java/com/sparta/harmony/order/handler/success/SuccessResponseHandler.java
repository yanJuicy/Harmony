package com.sparta.harmony.order.handler.success;

import com.sparta.harmony.order.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class SuccessResponseHandler {

    @ResponseBody
    public <T> ResponseEntity<ApiResponseDto<T>> handleSuccess(HttpStatus status, String message, List<T> data) {
        ApiResponseDto<T> response = new ApiResponseDto<>(
                status.value(),
                message,
                data
        );
        return ResponseEntity.status(status).body(response);
    }
}
