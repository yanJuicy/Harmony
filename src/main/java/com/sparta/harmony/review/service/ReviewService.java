package com.sparta.harmony.review.service;

import com.sparta.harmony.order.repository.OrderRepository;
import com.sparta.harmony.review.dto.ReviewRequestDto;
import com.sparta.harmony.review.dto.ReviewResponseDto;
import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.review.repository.ReviewRepository;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.store.repository.StoreRepository;
import com.sparta.harmony.user.entity.User;
import com.sparta.harmony.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    //리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, User user) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Order ID"));
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Store ID"));

        Review review = Review.builder()
                .user(user)
                .store(store)
                .order(order)
                .comment(requestDto.getComment())
                .rating(requestDto.getRating())
                .build();

        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    // 음식점 고유 ID로 리뷰 리스트 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByStoreId(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("음식점 정보를 찾을 수 없습니다"));

        // 삭제되지 않은 리뷰만 조회
        List<Review> reviews = reviewRepository.findByStoreAndDeletedFalse(store);

        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자 대한 리뷰 조회
    public List<ReviewResponseDto> getReviewsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        // 삭제되지 않은 리뷰만 조회
        List<Review> reviews = reviewRepository.findByUserAndDeletedFalse(user);
        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 주문 대한 리뷰 조회
    public ReviewResponseDto getReviewByOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다"));

        // 삭제되지 않은 리뷰만 조회
        Review review = reviewRepository.findByOrderAndDeletedFalse(order);
        return new ReviewResponseDto(review);
    }

    //리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, ReviewRequestDto requestDto, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with : " + reviewId));

        if(!review.getUser().equals(user)){
            throw new SecurityException("해당 리뷰 수정할 수가 없습니다.");
        }
        review.updateReview(requestDto.getComment(), requestDto.getRating());
        return new ReviewResponseDto(review);
    }

    @Transactional
    public void deleteReview(UUID reviewId, String deletedBy) {
        // 리뷰를 찾아서 삭제 처리 (실제로는 삭제하지 않고 숨김 처리)
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));

        review.deleteReview(deletedBy);  // Timestamped의 softDelete 호출

        reviewRepository.save(review);  // 업데이트된 리뷰 저장
    }
}
