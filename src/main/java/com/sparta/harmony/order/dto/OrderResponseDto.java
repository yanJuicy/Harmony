package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.menu.entity.Menu;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderMenu;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import com.sparta.harmony.user.entity.Address;
import com.sparta.harmony.user.entity.User;
import jakarta.validation.constraints.NotNull;
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

    @JsonProperty("data")
    private List<OrderResponseDataDto> orderMenuList = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order_date")
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user_id")
    private UUID userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order_status")
    private OrderStatusEnum orderStatus;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String postcode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("detail_address")
    private String detailAddress;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("special_request")
    private String specialRequest;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.storeName = order.getStore().getStoreName();
        this.totalAmount = order.getTotalAmount();
        this.paymentsId = order.getPayments().getPaymentsId();
        this.orderMenuList = order.getOrderMenuList().stream()
                .map(OrderResponseDataDto::new)
                .toList();
        this.postcode = order.getAddress().getPostcode();
        this.address = order.getAddress().getAddress();
        this.detailAddress = order.getAddress().getDetailAddress();
        this.createdAt = order.getCreatedAt();
    }

    public OrderResponseDto(Order order, User user) {
        this.orderId = order.getOrderId();
        this.userId = user.getUserId();
        this.storeName = order.getStore().getStoreName();
        this.totalAmount = order.getTotalAmount();
        this.paymentsId = order.getPayments().getPaymentsId();
        this.orderMenuList = order.getOrderMenuList().stream()
                .map(OrderResponseDataDto::new)
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
