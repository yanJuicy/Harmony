package com.sparta.harmony.order.controller;

import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.dto.ApiResponsePageDto;
import com.sparta.harmony.order.dto.*;
import com.sparta.harmony.common.handler.success.SuccessResponseHandler;
import com.sparta.harmony.order.service.OrderService;
import com.sparta.harmony.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // user 이상 사용 가능.
    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                                                        // security 적용 후 jwt 인증객체 받아오은걸로 변경 예정
                                                                        @RequestParam(value = "user_id") UUID userId) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto, userId);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.CREATED,
                "주문 요청이 성공적으로 접수되었습니다.",
                orderResponseDto);
    }

    // user 이상 사용 가능
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> cancelOrder(@PathVariable UUID orderId, User user) {
        OrderResponseDto orderResponseDto = orderService.softDeleteOrder(orderId, user);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "삭제 요청이 성공적으로 이루어졌습니다.",
                orderResponseDto);
    }

    // user 이상 사용 가능.
    @GetMapping("/orders")
    public ResponseEntity<ApiResponsePageDto<OrderResponseDto>> getOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "is_asc", defaultValue = "false") boolean isAsc,
            User user
    ) {
        Page<OrderResponseDto> orderResponseDto = orderService.getOrders(user, page - 1, size, sortBy, isAsc);

        return new SuccessResponseHandler().handlePageSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                orderResponseDto
        );
    }

    // user 이상 사용가능
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderDetailResponseDto>> getOrderByOrderId(@PathVariable UUID orderId, User user) {
        OrderDetailResponseDto orderDetailResponseDto = orderService.getOrderByOrderId(orderId, user);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                orderDetailResponseDto);
    }

    // owner 이상만 사용 가능
    @GetMapping("/orders/store/{storeId}")
    public ResponseEntity<ApiResponsePageDto<OrderResponseDto>> getOrderByStoreId(
            @PathVariable UUID storeId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "is_asc", defaultValue = "false") boolean isAsc) {
        Page<OrderResponseDto> orderResponseDto = orderService.getOrdersByStoreId(storeId, page - 1, size, sortBy, isAsc);

        return new SuccessResponseHandler().handlePageSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                orderResponseDto
        );
    }

    // owner 이상 사용 가능
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody @Valid OrderStatusRequestDto orderStatusDto
            ) {
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, orderStatusDto);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "수정이 완료되었습니다.",
                orderResponseDto
        );
    }
}
