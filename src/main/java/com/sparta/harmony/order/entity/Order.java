package com.sparta.harmony.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_order")
@NoArgsConstructor
public class Order extends Timestamped {

    @Id
    @Column(nullable = false)
    private UUID order_id;

//    @Column(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user_id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum order_status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum order_type;

    @Column(nullable = false)
    private String special_request;

    @Column(nullable = false)
    private String total_amount;

    @Embedded
    private Address address;


}
