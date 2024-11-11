package com.sparta.harmony.order.service;

import com.sparta.harmony.menu.repository.MenuRepository;
import com.sparta.harmony.order.dto.OrderMenuRequestDto;
import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import com.sparta.harmony.order.repository.OrderMenuRepository;
import com.sparta.harmony.order.repository.OrderRepository;
import com.sparta.harmony.order.repository.PaymentsRepository;
import com.sparta.harmony.store.repository.StoreRepository;
import com.sparta.harmony.user.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PaymentsRepository paymentsRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        Address address;

        // 유저의 권한에 따른 작업 및 address 값이 따로 안들어오면 user 정보에 있는 기본 주소로 받아오도록 설정하기

        address = Address.builder()
                .postcode(orderRequestDto.getPostcode())
                .address(orderRequestDto.getAddress())
                .detailAddress(orderRequestDto.getDetailAddress())
                .build();


        // 총 금액
        int total_price = 0;

        for (OrderMenuRequestDto menuItem : orderRequestDto.getOrderMenuList()) {
            int price = menuRepository.findById(menuItem.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다.")).getPrice();
            int quantity = menuItem.getQuantity();
            total_price += price * quantity;
        }

        Order order = Order.builder()
                .orderStatus(OrderStatusEnum.PENDING)
                .orderType(orderRequestDto.getOrderType())
                .specialRequest(orderRequestDto.getSpecialRequest())
                .totalAmount(total_price)
                .address(address)
//                .user(user)
                .store(storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(
                        () -> new IllegalArgumentException("해당 음식점이 없습니다.")
                ))
                .build();

        orderRepository.save(order);

        return new OrderResponseDto(order);

    }

}
