package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    @JsonProperty("order_id")
    private UUID orderId;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("total_amount")
    private int totalAmount;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.storeName = order.getStore().getStoreName();
        this.totalAmount = order.getTotalAmount();
    }
}
