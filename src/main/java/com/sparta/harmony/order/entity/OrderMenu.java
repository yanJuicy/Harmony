package com.sparta.harmony.order.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_order_menu")
@NoArgsConstructor
public class OrderMenu extends Timestamped {

    @Id
    @Column(nullable = false, name = "order_menu_id")
    private UUID OrderMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menuId", nullable = false)
//    private Menu menu;

    @Column(nullable = false)
    private int quantity;

//    @Builder
//    public OrderMenu(UUID OrderMenuId, Order order, Menu menu, int quantity) {
//        this.OrderMenuId = OrderMenuId;
//        this.order = order;
//        this.menu = menu;
//        this.quantity = quantity;
//    }
}
