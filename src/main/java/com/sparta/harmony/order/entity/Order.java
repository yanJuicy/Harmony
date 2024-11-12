package com.sparta.harmony.order.entity;

import com.sparta.harmony.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;
import java.util.List;

@Entity
@Getter
@Table(name = "p_order")
@NoArgsConstructor
public class Order extends Timestamped {

    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @Column(name = "order_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    @Column(length = 200, name = "special_request", nullable = false)
    private String specialRequest;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenuList;

    @Builder
    public Order(UUID orderId, User user, OrderStatusEnum orderStatus,
                 OrderTypeEnum orderType, String specialRequest,
                 int totalAmount, Address address, List<OrderMenu> orderMenuList) {
        this.orderId = orderId;
        this.user = user;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.specialRequest = specialRequest;
        this.totalAmount = totalAmount;
        this.address = address;
        this.orderMenuList = orderMenuList;
    }
}
