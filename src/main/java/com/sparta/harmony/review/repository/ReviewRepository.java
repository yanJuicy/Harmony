package com.sparta.harmony.review.repository;

import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository  extends JpaRepository<Review, UUID> {

    // 삭제되지 않은 특정 음식점의 리뷰 리스트 조회
    List<Review> findByStoreAndDeletedFalse(Store store);

    // 삭제되지 않은 특정 사용자의 리뷰 리스트 조회
    List<Review> findByUserAndDeletedFalse(User user);

    // 삭제되지 않은 특정 주문의 리뷰 조회
    Review findByOrderAndDeletedFalse(Order order);

    @Query("SELECT r.store.storeId, AVG(r.rating) " +
            "FROM Review r " +
            "WHERE r.store.storeId IN :storeIds " +
            "GROUP BY r.store.storeId")
    List<Object[]> findAverageRatingsByStoreIds(@Param("storeIds") List<UUID> storeIds);

    List<Review> findByStore(Store store);


}
