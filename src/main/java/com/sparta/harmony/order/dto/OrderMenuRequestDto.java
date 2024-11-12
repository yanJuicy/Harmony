package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderMenuRequestDto {

    @JsonProperty("menu_id")
    private UUID menuId;

    @JsonProperty("quantity")
    private int quantity;

    public OrderMenuRequestDto(OrderMenu orderMenu) {
        this.menuId = orderMenu.getMenu().getMenuId();
        this.quantity = orderMenu.getQuantity();
        
    }
}
