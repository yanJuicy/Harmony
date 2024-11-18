package com.sparta.harmony.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreSearchResponseDto {
    private String storeName;
    private double averageRating;

    public StoreSearchResponseDto(String storeName, double averageRating) {
        this.storeName = storeName;
        this.averageRating = averageRating;
    }
}
