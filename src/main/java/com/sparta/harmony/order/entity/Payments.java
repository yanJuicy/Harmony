package com.sparta.harmony.order.entity;

import com.sparta.harmony.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_payments")
@NoArgsConstructor
public class Payments extends Timestamped {

    @Id
    @Column(name = "payments_id", nullable = false)
    private UUID paymentsId;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int amount;

    @Builder
    public Payments(UUID paymentsId, Order order, User user, int amount) {
        this.paymentsId = paymentsId;
        this.order = order;
        this.user = user;
        this.amount = amount;
    }
}
