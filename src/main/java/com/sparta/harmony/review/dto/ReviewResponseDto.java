package com.sparta.harmony.review.dto;

import com.sparta.harmony.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private UUID reviewId;
    private String comment;
    private int rating;
    private UUID orderId;
    private UUID storeId;
    private UUID userId;

    public ReviewResponseDto(Review review){
        this.reviewId = review.getReviewId();
        this.comment = review.getComment();
        this.rating = review.getRating();

    }
}
