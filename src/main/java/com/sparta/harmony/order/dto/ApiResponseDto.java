package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {

    private int status;
    private String message;

    @JsonProperty("data")
    private List<T> data;
}
