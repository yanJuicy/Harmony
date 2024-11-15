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
public class OrderResponseOrderMenuListDto {

    @JsonProperty("menu_id")
    private UUID menuId;

    @JsonProperty("menu_name")
    private String menuName;

    @JsonProperty("quantity")
    private int quantity;

    public OrderResponseOrderMenuListDto(OrderMenu orderMenu) {
        this.menuId = orderMenu.getMenu().getMenuId();
        this.quantity = orderMenu.getQuantity();
        this.menuName = orderMenu.getMenu().getName();
    }
}
