package com.sparta.harmony.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "p_order")
@NoArgsConstructor
public class Order extends Timestamped {

    @Id
    @Column(nullable = false, name = "order_id")
    private UUID id;

    @Column(nullable = false, name = "user_id")
    private UUID userId;

    @Column(nullable = false, name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @Column(nullable = false, name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    @Column(nullable = false, name = "special_requests")
    private String specialRequest;

    @Column(nullable = false, name = "total_amount")
    private String totalAmount;

    @Embedded
    private Address address;
}
