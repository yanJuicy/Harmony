package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    @JsonProperty("order_id")
    private UUID orderId;

}
