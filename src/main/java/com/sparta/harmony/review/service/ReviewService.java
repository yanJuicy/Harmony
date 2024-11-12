package com.sparta.harmony.review.service;

import com.sparta.harmony.review.dto.ReviewRequestDto;
import com.sparta.harmony.review.dto.ReviewResponseDto;
import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.review.repository.ReviewRepository;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
//    private final OrderRepository orderRepository;
//    private final StoreRepository storeRepository;
//    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
//        Order order = orderRepository.findById(requestDto.getOrderId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Order ID"));
//        Store store = storeRepository.findById(requestDto.getStoreId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Store ID"));
//        User user = userRepository.findById(requestDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));

        Review review = Review.builder()
                .comment(requestDto.getComment())
                .rating(requestDto.getRating())
//                .order(order)
//                .store(store)
//                .user(user)
                .build();

        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto getReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.updateReview(requestDto.getComment(), requestDto.getRating());
        return new ReviewResponseDto(review);
    }

    @Transactional
    public void deleteReview(UUID reviewId, String deletedBy) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.softDelete(deletedBy);
    }
}
