package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "주문한 메뉴의 리스트")
@Getter
public class OrderMenuListRequestDto {

    @Schema(description = "주문 메뉴의 ID", example = "cf5ab475-6109-4416-b51f-aa85f8c35329")
    @NotEmpty
    @JsonProperty("menu_id")
    private UUID menuId;

    @Schema(description = "주문 메뉴의 양", example = "4")
    @NotEmpty
    @Positive
    @JsonProperty("quantity")
    private int quantity;
}
