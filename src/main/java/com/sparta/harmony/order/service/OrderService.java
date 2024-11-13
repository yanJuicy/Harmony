package com.sparta.harmony.order.service;

import com.sparta.harmony.menu.repository.MenuRepository;
import com.sparta.harmony.order.dto.OrderRequestDataDto;
import com.sparta.harmony.order.dto.OrderRequestDto;
import com.sparta.harmony.order.dto.OrderResponseDto;
import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderMenu;
import com.sparta.harmony.order.entity.OrderStatusEnum;
import com.sparta.harmony.order.entity.Payments;
import com.sparta.harmony.order.handler.exception.OrderNotFoundException;
import com.sparta.harmony.order.repository.OrderMenuRepository;
import com.sparta.harmony.order.repository.OrderRepository;
import com.sparta.harmony.order.repository.PaymentsRepository;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.store.repository.StoreRepository;
import com.sparta.harmony.user.entity.Address;
import com.sparta.harmony.user.entity.Role;
import com.sparta.harmony.user.entity.User;
import com.sparta.harmony.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PaymentsRepository paymentsRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto,
                                        // sercurity 적용 후 jwt로 인증 객체 받아오는걸로 적용할 예정
                                        UUID userId) {
        // sercurity 적용 후 jwt로 인증 객체 받아오는걸로 적용할 예정
        User userInfo = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("유저 정보를 확인해 주세요"));

        Address address;
        // address 값이 따로 안들어오면 user 정보에 있는 기본 주소로 받아오도록 설정하기
        if ((orderRequestDto.getAddress().isEmpty())
                && (orderRequestDto.getDetailAddress().isEmpty())) {
            // 주소지가 따로 입력되지 않은 경우
            Address basicUserAddress = userInfo.getAddress();

            address = buildAddressUseAddress(basicUserAddress);
        } else {
            // 주소지가 입력된 경우
            address = buildAddressUseDto(orderRequestDto);
        }

        int total_price = getTotalPrice(orderRequestDto);

        Order order = buildOrder(orderRequestDto, address, userInfo, total_price);

        Payments payments = buildPayments(userInfo, total_price, order);

        buildMenuList(orderRequestDto, order);

        order.addPayments(payments);
        userInfo.addOrder(order);
        userInfo.addPayments(payments);

        orderRepository.save(order);

        return new OrderResponseDto(order);

    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrders(User user, OrderRequestDto orderRequestDto, int page, int size,
                                            String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 권한에 따른 조회. User일 경우 자기 주문내역, Owner의 경우 가게의 주문내역, manager이상의 경우 모든 주문 내역
        Role userRoleEnum = user.getRole();

        Page<Order> orderList;

        if (userRoleEnum == Role.USER) {
            orderList = orderRepository.findAllByUserAndDeletedByFalse(user, pageable);
        } else if (userRoleEnum == Role.OWNER) {
            Store store = Store.builder()
                    .storeId(orderRequestDto.getStoreId())
                    .build();

            orderList = orderRepository.findAllByStoreAndDeletedByFalse(store, pageable);
        } else {
            orderList = orderRepository.findAllByDeletedFalse(pageable);
        }

        return orderList.map(OrderResponseDto::new);
    }

    public OrderResponseDto getOrderByOrderId(UUID orderId, User user) {

        // orderId를 가지고 와서 해당 주문에 대한 상세 조회.
        // customer, owner의 경우 해당 유저만이 자신의 주문을 확인 가능. 단, manager, master의 경우 모둔 주문 상세조회

        Role userRoleEnum = user.getRole();
        Order order;

        if (userRoleEnum == Role.USER || userRoleEnum == Role.OWNER) {
            order = orderRepository.findByOrderIdAndUserAndDeletedFalse(orderId, user).orElseThrow(()
                    -> new OrderNotFoundException("고객님의 주문 내용이 있는지 확인해주세요."));
        } else {
            order = orderRepository.findByOrderIdAndDeletedFalse(orderId).orElseThrow(()
                    -> new OrderNotFoundException("없는 주문 번호 입니다."));
        }

        return new OrderResponseDto(order, user);
    }

    @Transactional
    public OrderResponseDto softDeleteOrder(UUID orderId, User user) {

        // Jwt에서 받아온 유저 정보와 client요청에서 넘어온 유저 ID가 일치한지 확인 후 주문 취소 진행 필요

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다."));

//        User testUser = getTestUser();

//        order.softDelete(testUser.getEmail());
        orderRepository.save(order);

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .orderId(orderId)
                .build();

        return orderResponseDto;
    }

    private void buildMenuList(OrderRequestDto orderRequestDto, Order order) {
        for (OrderRequestDataDto menuItem : orderRequestDto.getOrderMenuList()) {
            OrderMenu orderMenu = OrderMenu.builder()
                    .quantity(menuItem.getQuantity())
                    .order(order)
                    .menu(menuRepository.findById(menuItem.getMenuId()).orElseThrow(() -> new IllegalArgumentException("해당 주문 메뉴 ID는 없습니다.")))
                    .build();
            order.addOrderMenu(orderMenu);
        }
    }

    private Address buildAddressUseAddress(Address basicUserAddress) {
        Address address;
        address = Address.builder()
                .postcode(basicUserAddress.getPostcode())
                .address(basicUserAddress.getAddress())
                .detailAddress(basicUserAddress.getDetailAddress())
                .build();
        return address;
    }

    private Address buildAddressUseDto(OrderRequestDto orderRequestDto) {
        Address address;
        address = Address.builder().postcode(orderRequestDto.getPostcode()).address(orderRequestDto.getAddress()).detailAddress(orderRequestDto.getDetailAddress()).build();
        return address;
    }

    private Payments buildPayments(User userInfo, int total_price, Order order) {
        Payments payments = Payments.builder()
                .user(userInfo)
                .order(order)
                .amount(total_price).build();
        return payments;
    }

    private Order buildOrder(OrderRequestDto orderRequestDto, Address address, User userInfo, int total_price) {
        Order order = Order.builder()
                .orderStatus(OrderStatusEnum.PENDING)
                .orderType(orderRequestDto.getOrderType())
                .specialRequest(orderRequestDto.getSpecialRequest())
                .totalAmount(total_price).address(address)
                .user(userInfo)
                // 초기화 안해주면 null값 들어가서 값을 못가져옴. 반드시 초기화 필요
                .orderMenuList(new ArrayList<>())
                .store(storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 음식점이 없습니다.")))
                .build();
        return order;
    }


    private int getTotalPrice(OrderRequestDto orderRequestDto) {
        int total_price = 0;

        for (OrderRequestDataDto menuItem : orderRequestDto.getOrderMenuList()) {
            int price = menuRepository.findById(menuItem.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다.")).getPrice();
            int quantity = menuItem.getQuantity();
            total_price += price * quantity;
        }
        return total_price;
    }
}
