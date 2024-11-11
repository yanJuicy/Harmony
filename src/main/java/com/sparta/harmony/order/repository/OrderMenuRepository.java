package com.sparta.harmony.order.repository;

import com.sparta.harmony.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, UUID> {
}
