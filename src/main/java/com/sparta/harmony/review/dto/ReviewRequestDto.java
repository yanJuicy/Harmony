package com.sparta.harmony.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private UUID userId;
    private UUID storeId;
    private UUID orderId;
    private String comment;
    private int rating;


}
