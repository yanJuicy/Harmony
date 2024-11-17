package com.sparta.harmony.order.controller;

import com.sparta.harmony.common.dto.ApiPageResponseDto;
import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.handler.success.SuccessResponseHandler;
import com.sparta.harmony.order.dto.OrderDetailResponseDto;
import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.dto.OrderStatusRequestDto;
import com.sparta.harmony.order.service.OrderService;
import com.sparta.harmony.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // user 이상 사용 가능.
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto, userDetails.getUser());

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.CREATED,
                "주문 요청이 성공적으로 접수되었습니다.",
                orderResponseDto);
    }

    // user 이상 사용 가능
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> cancelOrder(@PathVariable UUID orderId,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderResponseDto orderResponseDto = orderService.softDeleteOrder(orderId, userDetails.getUser());

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "삭제 요청이 성공적으로 이루어졌습니다.",
                orderResponseDto);
    }

    // user 이상 사용 가능.
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @GetMapping("/orders")
    public ResponseEntity<ApiPageResponseDto<OrderResponseDto>> getOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "is_asc", defaultValue = "false") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<OrderResponseDto> orderResponseDto = orderService.getOrders(userDetails.getUser(), page - 1, size, sortBy, isAsc);

        return new SuccessResponseHandler().handlePageSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                orderResponseDto
        );
    }

    // user 이상 사용가능
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderDetailResponseDto>> getOrderByOrderId(@PathVariable UUID orderId,
                                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderDetailResponseDto orderDetailResponseDto = orderService.getOrderByOrderId(orderId, userDetails.getUser());

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                orderDetailResponseDto);
    }

    // owner 이상만 사용 가능
    @PreAuthorize("hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @GetMapping("/orders/store/{storeId}")
    public ResponseEntity<ApiPageResponseDto<OrderResponseDto>> getOrderByStoreId(
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
    @PreAuthorize("hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
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
