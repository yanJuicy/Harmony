package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {

    private int status;
    private String message;

    private int page;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int size;

    @JsonProperty("total_elements")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long totalElements;

    @JsonProperty("total_page")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int totalPage;

    @JsonProperty("data")
    private List<T> data;

    public ApiResponseDto(int status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
