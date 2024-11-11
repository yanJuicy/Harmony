package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    @JsonProperty("order_id")
    private UUID orderId;

    @JsonProperty("menu_id")
    private List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();

    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    private int quantity;

    private String postcode;

    private String address;

    @JsonProperty("detail_address")
    private String detailAddress;

    @JsonProperty("special_request")
    private String specialRequest;
}
