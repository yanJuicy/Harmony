package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.harmony.order.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderRequestDataDto {

    @JsonProperty("menu_id")
    private UUID menuId;

    @JsonProperty("quantity")
    private int quantity;

    public OrderRequestDataDto(OrderMenu orderMenu) {
        this.menuId = orderMenu.getMenu().getMenuId();
        this.quantity = orderMenu.getQuantity();
        
    }
}
