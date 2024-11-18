package com.sparta.harmony.menu.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MenuResponseDto {

    private UUID storeId;
    private UUID menuId;
    private String name;
    private String description;
    private int price;
    private boolean isAvailable;
    private String imageUrl;

    @Builder
    public MenuResponseDto(UUID storeId, UUID menuId, String name, String description,
                           int price, boolean isAvailable, String imageUrl) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
    }
}
