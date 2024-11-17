package com.sparta.harmony.review.controller;

import com.sparta.harmony.review.dto.ReviewRequestDto;
import com.sparta.harmony.review.dto.ReviewResponseDto;
import com.sparta.harmony.review.service.ReviewService;
import com.sparta.harmony.security.UserDetailsImpl;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 음식점 ID로 해당 음식점의 리뷰 리스트 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByStoreId(@PathVariable UUID storeId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    // 특정 사용자에 대한 리뷰 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByUser(@PathVariable UUID userId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    // 특정 주문에 대한 리뷰 조회
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ReviewResponseDto> getReviewByOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(reviewService.getReviewByOrder(orderId));
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    // 리뷰 삭제
//    @DeleteMapping("/{reviewId}")
//    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId) {
//        reviewService.deleteReview(reviewId);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID reviewId, @AuthenticationPrincipal User user) {
        // 인증된 사용자 확인
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized: User not logged in");
        }

        // 삭제 요청 처리
        String deletedBy = user.getUserName(); // 삭제한 사용자 정보
        reviewService.deleteReview(reviewId, deletedBy);

        return ResponseEntity.status(HttpStatus.OK).body("Review hidden successfully");
    }
}
