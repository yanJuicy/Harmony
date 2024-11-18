package com.sparta.harmony.store.repository;

import com.sparta.harmony.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    Page<Store> findByStoreNameContaining(String searchKeyword, Pageable pageable);

}
