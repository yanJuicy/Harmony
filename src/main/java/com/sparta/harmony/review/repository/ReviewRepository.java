package com.sparta.harmony.review.repository;

import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository  extends JpaRepository<Review, UUID> {

    List<Review> findByStore_StoreId(UUID storeId);

    List<Review> findByUser_UserId(UUID userId);

    Review findByOrder_OrderId(UUID orderId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.store.storeId = :storeId")
    Optional<Double> findAverageRatingByStoreId(UUID storeId);

    List<Review> findByStore(Store store);


}
