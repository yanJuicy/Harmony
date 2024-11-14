package com.sparta.harmony.order.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Payments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsResponseDto {

    @JsonProperty("payments_id")
    private UUID storeId;

    @JsonProperty("order_id")
    private UUID orderId;

    private int amount;

    public PaymentsResponseDto(Payments payments) {
        this.storeId = payments.getPaymentsId();
        this.orderId = payments.getOrder().getOrderId();
        this.amount = payments.getAmount();
    }
}
