package com.sparta.harmony.order.service;

import com.sparta.harmony.menu.repository.MenuRepository;
import com.sparta.harmony.order.dto.OrderMenuRequestDto;
import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import com.sparta.harmony.order.entity.Payments;
import com.sparta.harmony.order.repository.OrderMenuRepository;
import com.sparta.harmony.order.repository.OrderRepository;
import com.sparta.harmony.order.repository.PaymentsRepository;
import com.sparta.harmony.store.repository.StoreRepository;
import com.sparta.harmony.user.entity.Address;
import com.sparta.harmony.user.entity.Role;
import com.sparta.harmony.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PaymentsRepository paymentsRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {

        Address address;

        // 유저의 권한에 따른 작업 및 address 값이 따로 안들어오면 user 정보에 있는 기본 주소로 받아오도록 설정하기

        address = Address.builder().postcode(orderRequestDto.getPostcode()).address(orderRequestDto.getAddress()).detailAddress(orderRequestDto.getDetailAddress()).build();


//        User testUser = getTestUser();

        // 총 금액
        int total_price = getTotalPrice(orderRequestDto);

        Order order = Order.builder().orderStatus(OrderStatusEnum.PENDING).orderType(orderRequestDto.getOrderType()).specialRequest(orderRequestDto.getSpecialRequest()).totalAmount(total_price).address(address)
//                .user(testUser)
                .store(storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 음식점이 없습니다."))).build();

        Payments payments = Payments.builder()
//                .user(testUser)
                .order(order)
                .amount(total_price).build();

        orderRepository.save(order);
        paymentsRepository.save(payments);

        return new OrderResponseDto(order);

    }

    @Transactional
    public OrderResponseDto softDeleteOrder(UUID orderId, User user) {

        // Jwt에서 받아온 유저 정보와 client요청에서 넘어온 유저 ID가 일치한지 확인 후 주문 취소 진행 필요

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

//        User testUser = getTestUser();

//        order.softDelete(testUser.getEmail());
        orderRepository.save(order);

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .orderId(orderId)
                .build();

        return orderResponseDto;
    }

    private int getTotalPrice(OrderRequestDto orderRequestDto) {
        int total_price = 0;

        for (OrderMenuRequestDto menuItem : orderRequestDto.getOrderMenuList()) {
            int price = menuRepository.findById(menuItem.getMenuId()).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다.")).getPrice();
            int quantity = menuItem.getQuantity();
            total_price += price * quantity;
        }
        return total_price;
    }

    private User getTestUser() {
        return User.builder()
                .userId(UUID.fromString("fe40d0de-0d5f-4b22-ad45-922d6088b722"))
                .password("123")
                .userName("test")
                .email("test@test.com")
                .role(Role.USER)
                .build();
    }


}
