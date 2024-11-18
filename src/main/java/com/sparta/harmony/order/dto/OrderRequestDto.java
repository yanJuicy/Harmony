package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Schema(description = "주문 요청 Dto")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderRequestDto {

    @Schema(description = "가게 ID", example = "cab802e2-d172-4ddb-8e25-d2787122a9cc")
    @NotNull(message = "가게 id는 필수입니다.")
    @JsonProperty("store_id")
    private UUID storeId;

    @Schema(description = "주문 메뉴 리스트")
    @NotNull(message = "메뉴 선택은 필수입니다.")
    @JsonProperty("order_menu_list")
    private List<OrderMenuListRequestDto> orderMenuList = new ArrayList<>();

    @Schema(description = "주문 타입", example = "DELIVERY")
    @NotNull(message = "배달/포장 선택은 필수입니다.")
    @JsonProperty("order_type")
    private OrderTypeEnum orderType;

    @Schema(description = "우편 번호", example = "123123")
    private String postcode;

    @Schema(description = "주소", example = "서울특별시 관악구")
    private String address;

    @Schema(description = "상세 주소", example = "00로 00길 00-0 000호")
    @JsonProperty("detail_address")
    private String detailAddress;

    @Schema(description = "요청 사항", example = "배달 도착 시 집앞에 놔두시고 문만 두드려 주세요")
    @Size(max = 200, message = "요청사항은 최대 200글자입니다.")
    @JsonProperty("special_request")
    private String specialRequest;
}
