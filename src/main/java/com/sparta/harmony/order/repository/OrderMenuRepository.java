package com.sparta.harmony.order.repository;

import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, UUID> {
    List<OrderMenu> findAllByOrder(Order order);
}
