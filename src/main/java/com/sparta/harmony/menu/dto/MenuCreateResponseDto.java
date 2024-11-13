package com.sparta.harmony.menu.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MenuCreateResponseDto {

    private UUID menuId;
    private UUID storeId;
    private boolean isAvailable;
    private String name;
    private String description;
    private int price;
    private String imageUrl;

    @Builder
    public MenuCreateResponseDto(UUID menuId, UUID storeId, boolean isAvailable,
                                 String name, String description, int price, String imageUrl) {
        this.menuId = menuId;
        this.storeId = storeId;
        this.isAvailable = isAvailable;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
