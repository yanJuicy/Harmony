package com.sparta.harmony.order.repository;

import com.sparta.harmony.order.entity.Payments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PaymentsRepositoryCustom {
    Page<Payments> findPaymentsByStoreIdAndDeletedFalse(UUID storeId, Pageable pageable);
}
