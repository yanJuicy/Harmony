package com.sparta.harmony.review.entity;

import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.Timestamped;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_review")
@NoArgsConstructor
public class Review extends Timestamped {

    @Id
    @Column(name = "review_id", nullable = false)
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 200)
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Builder
    public Review(UUID reviewId, Order order, Store store, User user, String comment, int rating) {
        this.reviewId = reviewId;
        this.order = order;
        this.store = store;
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }
}
