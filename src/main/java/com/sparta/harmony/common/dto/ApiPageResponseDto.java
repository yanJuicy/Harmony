package com.sparta.harmony.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiPageResponseDto<T> {

    private int status;
    private String message;

    private int page;

    private int size;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("total_page")
    private int totalPage;

    @JsonProperty("data")
    private List<T> data;
}
