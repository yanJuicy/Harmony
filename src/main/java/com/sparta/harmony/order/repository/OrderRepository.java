package com.sparta.harmony.order.repository;

import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByUser(User user, Pageable pageable);

    Page<Order> findAllByUserAndDeletedByFalse(User user, Pageable pageable);

    Page<Order> findAllByStore(Store store, Pageable pageable);

    Page<Order> findAllByStoreAndDeletedByFalse(Store store, Pageable pageable);

    Page<Order> findAllByDeletedFalse(Pageable pageable);
}
