package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class OrderStatusDto {

    @NotEmpty
    @JsonProperty("order_status")
    private OrderStatusEnum orderStatus;
}
