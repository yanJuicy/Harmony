package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import com.sparta.harmony.user.entity.User;
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
public class OrderDetailResponseDto {

    @JsonProperty("order_id")
    private UUID orderId;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("total_amount")
    private int totalAmount;

    @JsonProperty("payments_id")
    private UUID paymentsId;

    @JsonProperty("order_menu_list")
    private List<OrderResponseOrderMenuListDto> orderMenuList = new ArrayList<>();

    @JsonProperty("order_date")
    private LocalDateTime createdAt;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    @JsonProperty("order_status")
    private OrderStatusEnum orderStatus;

    private String postcode;

    private String address;

    @JsonProperty("detail_address")
    private String detailAddress;

    @JsonProperty("special_request")
    private String specialRequest;

    public OrderDetailResponseDto(Order order, User user) {
        this.orderId = order.getOrderId();
        this.userId = user.getUserId();
        this.storeName = order.getStore().getStoreName();
        this.totalAmount = order.getTotalAmount();
        this.paymentsId = order.getPayments().getPaymentsId();
        this.orderMenuList = order.getOrderMenuList().stream()
                .map(OrderResponseOrderMenuListDto::new)
                .toList();
        this.createdAt = order.getCreatedAt();
        this.orderType = order.getOrderType();
        this.orderStatus = order.getOrderStatus();
        this.postcode = order.getAddress().getPostcode();
        this.address = order.getAddress().getAddress();
        this.detailAddress = order.getAddress().getDetailAddress();
        this.specialRequest = order.getSpecialRequest();
    }
}
