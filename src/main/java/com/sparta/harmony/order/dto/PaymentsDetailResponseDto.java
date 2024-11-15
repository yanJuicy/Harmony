package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Payments;
import com.sparta.harmony.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsDetailResponseDto {

    @JsonProperty("payments_id")
    private UUID paymentsId;

    @JsonProperty("store_id")
    private UUID storeId;

    @JsonProperty("order_id")
    private UUID orderId;

    private String email;

    private int amount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("store_name")
    private String storeName;

    private List<OrderResponseOrderMenuListDto> orderMenuList = new ArrayList<>();

    public PaymentsDetailResponseDto(Payments payments) {
        this.paymentsId = payments.getPaymentsId();
        this.storeId = payments.getOrder().getStore().getStoreId();
        this.orderId = payments.getOrder().getOrderId();
        this.email = payments.getUser().getEmail();
        this.storeName = payments.getOrder().getStore().getStoreName();
        this.orderMenuList = payments.getOrder().getOrderMenuList().stream()
                .map(OrderResponseOrderMenuListDto::new)
                .toList();
        this.amount = payments.getAmount();
        this.createdAt = payments.getCreatedAt();
    }
}
