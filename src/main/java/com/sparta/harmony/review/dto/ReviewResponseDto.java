package com.sparta.harmony.review.dto;

import com.sparta.harmony.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private UUID reviewId;
    private UUID userId;
    private UUID storeId;
    private UUID orderId;
    private String comment;
    private int rating;




    public ReviewResponseDto(Review review){
        this.reviewId = review.getReviewId();
        this.userId = review.getUser().getUserId();
        this.storeId = review.getStore().getStoreId();
        this.orderId = review.getOrder().getOrderId();
        this.comment = review.getComment();
        this.rating = review.getRating();

    }
}
