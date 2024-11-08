package com.sparta.harmony.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_category_id;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime updated_at;
    private String updated_by;
    private LocalDateTime deleted_at;
    private String deleted_by;

    @Column(nullable = false)
    private String category_name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategory> storeCategories = new ArrayList<>();

    @Builder
    public Category(LocalDateTime created_at, String created_by,
                         LocalDateTime updated_at, String updated_by,
                         LocalDateTime deleted_at, String deleted_by,
                         Long store_category_id, String category_name, Store store) {
        this.store_category_id = store_category_id;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
        this.deleted_at = deleted_at;
        this.deleted_by = deleted_by;
        this.category_name = category_name;
    }
}
