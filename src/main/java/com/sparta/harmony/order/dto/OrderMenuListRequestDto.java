package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderMenuListRequestDto {

    @NotEmpty
    @JsonProperty("menu_id")
    private UUID menuId;

    @NotEmpty
    @Positive
    @JsonProperty("quantity")
    private int quantity;
}
