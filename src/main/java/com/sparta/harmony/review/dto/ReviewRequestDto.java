package com.sparta.harmony.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private String comment;
    private int rating;
    private UUID orderId;
    private UUID storeId;
    public ReviewRequestDto(UUID storeId, int rating, String comment) {
        this.storeId = storeId;
        this.rating = rating;
        this.comment = comment;
    }
}
