package com.sparta.harmony.store.dto;

import com.sparta.harmony.review.dto.ReviewResponseDto;
import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.user.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailResponseDto {
    private String storeName;
    private String phoneNumber;
    private Address address;
    private List<ReviewResponseDto> reviews;

    public StoreDetailResponseDto(Store store, List<Review> reviews) {
        this.storeName = store.getStoreName();
        this.phoneNumber = store.getPhoneNumber();
        this.address = store.getAddress();
        this.reviews = reviews.stream()
                .map(review -> new ReviewResponseDto(review))
                .collect(Collectors.toList());
    }
}
