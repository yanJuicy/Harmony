package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "가게 id는 필수입니다.")
    @JsonProperty("store_id")
    private UUID storeId;

    @NotEmpty(message = "메뉴 선택은 필수입니다.")
    @JsonProperty("order_menu_list")
    private List<OrderRequestOrderMenuListDto> orderMenuList = new ArrayList<>();

    @NotEmpty(message = "배달/포장 선택은 필수입니다.")
    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    private String postcode;

    private String address;

    @JsonProperty("detail_address")
    private String detailAddress;

    @Max(value = 200, message = "요청사항은 최대 200글자입니다.")
    @JsonProperty("special_request")
    private String specialRequest;
}
