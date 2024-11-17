package com.sparta.harmony.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_store_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID storeCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public StoreCategory(Store store, Category category) {
        this.store = store;
        this.category = category;
    }
}
