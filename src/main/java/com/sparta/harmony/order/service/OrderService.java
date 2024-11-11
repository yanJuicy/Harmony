package com.sparta.harmony.order.service;

import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.repository.OrderMenuRepository;
import com.sparta.harmony.order.repository.OrderRepository;
import com.sparta.harmony.order.repository.PaymentsRepository;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PaymentsRepository paymentsRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto, User user) {
        return null;
    }

}
