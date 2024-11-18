package com.sparta.harmony.review.controller;

import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.handler.success.SuccessResponseHandler;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.review.dto.ReviewRequestDto;
import com.sparta.harmony.review.dto.ReviewResponseDto;
import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.review.service.ReviewService;
import com.sparta.harmony.security.UserDetailsImpl;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final SuccessResponseHandler successResponseHandler;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> createReview(@RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, user);
        return successResponseHandler.handleSuccess(HttpStatus.CREATED, "리뷰 생성 성공", responseDto);
    }


    // 음식점 ID로 해당 음식점의 리뷰 리스트 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<ApiResponseDto<List<ReviewResponseDto>>> getReviewsByStoreId(@PathVariable UUID storeId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByStoreId(storeId);
        return successResponseHandler.handleSuccess(HttpStatus.OK, "음식점 리뷰 조회 성공", reviews);
    }

    // 특정 사용자에 대한 리뷰 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<ReviewResponseDto>>> getReviewsByUser(@PathVariable UUID userId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByUser(userId);
        return successResponseHandler.handleSuccess(HttpStatus.OK, "사용자 리뷰 조회 성공", reviews);
    }

    // 특정 주문에 대한 리뷰 조회
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> getReviewByOrder(@PathVariable UUID orderId) {
        ReviewResponseDto responseDto = reviewService.getReviewByOrder(orderId);
        return successResponseHandler.handleSuccess(HttpStatus.OK, "주문 리뷰 조회 성공", responseDto);
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, userDetails.getUser());
        return successResponseHandler.handleSuccess(HttpStatus.OK, "리뷰 수정 성공", responseDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<Object>> deleteReview(@PathVariable UUID reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 인증된 사용자 확인
        if (userDetails == null) {
            return successResponseHandler.handleSuccess(HttpStatus.FORBIDDEN, "Unauthorized: User not logged in", null);
        }

        // 삭제 요청 처리
        String deletedBy = userDetails.getUsername(); // 삭제한 사용자 정보
        reviewService.deleteReview(reviewId, deletedBy);

        return successResponseHandler.handleSuccess(HttpStatus.OK, "Review hidden successfully", null);
    }
}
