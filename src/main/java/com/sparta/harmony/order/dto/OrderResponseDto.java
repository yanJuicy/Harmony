package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order_id")
    private UUID orderId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("store_name")
    private String storeName;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("total_amount")
    private int totalAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payments_id")
    private UUID paymentsId;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.storeName = order.getStore().getStoreName();
        this.totalAmount = order.getTotalAmount();
    }
}
