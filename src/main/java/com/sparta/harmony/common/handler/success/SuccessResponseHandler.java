package com.sparta.harmony.common.handler.success;

import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.dto.ApiPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class SuccessResponseHandler {

    @ResponseBody
    public <T> ResponseEntity<ApiResponseDto<T>> handleSuccess(HttpStatus status, String message, T dto) {
        ApiResponseDto<T> response = new ApiResponseDto<>(
                status.value(),
                message,
                dto
        );
        return ResponseEntity.status(status).body(response);
    }

    @ResponseBody
    public <T> ResponseEntity<ApiPageResponseDto<T>> handlePageSuccess(HttpStatus status, String message, Page<T> page) {
        List<T> content = page.getContent(); // 현재 페이지의 데이터

        ApiPageResponseDto<T> response = new ApiPageResponseDto<>(
                status.value(),
                message,
                page.getNumber(), // 현재 페이지 번호
                page.getSize(), // 페이지 크기
                page.getTotalElements(), // 전체 요소 수
                page.getTotalPages(), // 전체 페이지 수
                content
        );
        return ResponseEntity.status(status).body(response);
    }
}
