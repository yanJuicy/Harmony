package com.sparta.harmony.order.controller;

import com.sparta.harmony.order.dto.ApiResponseDto;
import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.handler.success.SuccessResponseHandler;
import com.sparta.harmony.order.service.OrderService;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto orderRequestDto) {

        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.CREATED,
                "주문 요청이 성공적으로 접수되었습니다. 가게의 수락을 기다려 주세요.",
                List.of(orderResponseDto));
    }


}
