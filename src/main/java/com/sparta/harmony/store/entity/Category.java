package com.sparta.harmony.store.entity;

import com.sparta.harmony.order.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID store_category_id;

    @Column(nullable = false)
    private String category_name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategory> storeCategories = new ArrayList<>();

    @Builder
    public Category(UUID store_category_id, String category_name, List<StoreCategory> storeCategories) {
        this.store_category_id = store_category_id;
        this.category_name = category_name;
        this.storeCategories = storeCategories;
    }
}
