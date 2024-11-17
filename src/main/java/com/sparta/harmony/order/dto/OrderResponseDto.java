package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderResponseDto {

    @JsonProperty("order_id")
    private UUID orderId;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("total_amount")
    private int totalAmount;

    @JsonProperty("order_menu_list")
    private List<OrderMenuListResponseDto> orderMenuList = new ArrayList<>();

    @JsonProperty("order_date")
    private LocalDateTime createdAt;

    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    @JsonProperty("order_status")
    private OrderStatusEnum orderStatus;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.storeName = order.getStore().getStoreName();
        this.totalAmount = order.getTotalAmount();
        this.orderMenuList = order.getOrderMenuList().stream()
                .map(OrderMenuListResponseDto::new)
                .toList();
        this.orderType = order.getOrderType();
        this.orderStatus = order.getOrderStatus();
        this.createdAt = order.getCreatedAt();
    }
}
