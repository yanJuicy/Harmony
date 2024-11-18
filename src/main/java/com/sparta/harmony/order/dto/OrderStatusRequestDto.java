package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "주문 상태 요청 Dto")
@Getter
public class OrderStatusRequestDto {

    @Schema(description = "주문 상태", example = "DELIVERING")
    @NotNull(message = "주문 상태를 선택해주세요.")
    @JsonProperty("order_status")
    private OrderStatusEnum orderStatus;
}
