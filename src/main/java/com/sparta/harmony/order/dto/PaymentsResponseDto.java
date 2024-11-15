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
    private UUID paymentsId;

    @JsonProperty("store_id")
    private UUID storeId;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("order_id")
    private UUID orderId;

    private int amount;

    public PaymentsResponseDto(Payments payments) {
        this.paymentsId = payments.getPaymentsId();
        this.storeId = payments.getOrder().getStore().getStoreId();
        this.storeName = payments.getOrder().getStore().getStoreName();
        this.orderId = payments.getOrder().getOrderId();
        this.amount = payments.getAmount();
    }
}
