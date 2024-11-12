package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "음식점 ID는 필수입니다.")
    @JsonProperty("store_id")
    private UUID storeId;

    @NotNull(message = "메뉴 ID는 필수입니다.")
    @JsonProperty("data")
    private List<OrderMenuRequestDto> orderMenuList = new ArrayList<>();

    @NotNull(message = "포장 유형은 필수입니다.")
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
                .map(OrderMenuRequestDto::new)
                .toList();
        this.orderType = order.getOrderType();
        this.postcode = order.getAddress().getPostcode();
        this.address = order.getAddress().getAddress();
        this.detailAddress = order.getAddress().getDetailAddress();
        this.specialRequest = order.getSpecialRequest();
    }
}
