package com.sparta.harmony.order.repository;

import com.sparta.harmony.order.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
}
