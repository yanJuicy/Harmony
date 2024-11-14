package com.sparta.harmony.order.repository;

import com.sparta.harmony.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, UUID> {
}
