package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {

    @NotNull(message = "주문 상태를 선택해주세요.")
    @JsonProperty("order_status")
    private OrderStatusEnum orderStatus;
}
