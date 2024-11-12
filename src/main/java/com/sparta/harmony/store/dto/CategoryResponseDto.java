package com.sparta.harmony.store.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CategoryResponseDto {
    private UUID categoryId;
    private String categoryName;

    public CategoryResponseDto(UUID categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
