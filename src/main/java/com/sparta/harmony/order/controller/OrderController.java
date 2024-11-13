package com.sparta.harmony.order.controller;

import com.sparta.harmony.order.dto.ApiResponseDto;
import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.handler.success.SuccessResponseHandler;
import com.sparta.harmony.order.service.OrderService;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                                                        // security 적용 후 jwt 인증객체 받아오은걸로 변경 예정
                                                                        @RequestParam(value = "user_id") UUID userId) {

        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto, userId);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.CREATED,
                "주문 요청이 성공적으로 접수되었습니다. 가게의 수락을 기다려 주세요.",
                List.of(orderResponseDto));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> cancelOrder(@PathVariable UUID orderId, User user) {

        OrderResponseDto orderResponseDto = orderService.softDeleteOrder(orderId, user);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "삭제 요청이 성공적으로 이루어졌습니다.",
                List.of(orderResponseDto)
        );
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> getOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort_by", defaultValue = "orderStatus") String sortBy,
            @RequestParam(value = "is_asc", defaultValue = "true") boolean isAsc,
            @RequestParam(value = "order_id", required = false) UUID orderId,
            @RequestParam(value = "store_id", required = false) UUID storeId,
            User user
    ) {

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .orderId(orderId)
                .storeId(storeId)
                .build();

        Page<OrderResponseDto> orderResponseDto = orderService.getOrders(user, orderRequestDto, page - 1, size, sortBy, isAsc);

        return new SuccessResponseHandler().handlePageSuccess(
                HttpStatus.OK,
                "조회가 완료되었습니다.",
                orderResponseDto
        );
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> getOrderByOrderId(@PathVariable UUID orderId, User user) {

        OrderResponseDto orderResponseDto = orderService.getOrderByOrderId(orderId, user);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                List.of(orderResponseDto));
    }

}
