package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiDetailResponseDto<T> {

    private int status;
    private String message;

    @JsonProperty("data")
    private OrderDetailResponseDto orderDetailResponseDto;

}
