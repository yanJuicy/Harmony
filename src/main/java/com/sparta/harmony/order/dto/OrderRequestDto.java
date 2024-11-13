package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderRequestDto {

    @JsonProperty("store_id")
    private UUID storeId;

    @JsonProperty("order_id")
    private UUID orderId;

    @JsonProperty("data")
    private List<OrderRequestDataDto> orderMenuList = new ArrayList<>();

    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    private String postcode;

    private String address;

    @JsonProperty("detail_address")
    private String detailAddress;

    @JsonProperty("special_request")
    private String specialRequest;

    public OrderRequestDto(Order order) {
        this.storeId = order.getStore().getStoreId();
        this.orderMenuList = order.getOrderMenuList().stream()
                .map(OrderRequestDataDto::new)
                .toList();
        this.orderType = order.getOrderType();
        this.postcode = order.getAddress().getPostcode();
        this.address = order.getAddress().getAddress();
        this.detailAddress = order.getAddress().getDetailAddress();
        this.specialRequest = order.getSpecialRequest();
    }
}
