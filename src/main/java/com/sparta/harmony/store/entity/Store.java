package com.sparta.harmony.store.entity;

import com.sparta.harmony.user.entity.StoreAddress;
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
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_id;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime updated_at;
    private String updated_by;
    private LocalDateTime deleted_at;
    private String deleted_by;

    @Column(nullable = false)
    private String store_name;

    @Column(nullable = false)
    private String phone_number;

    @Embedded
    private StoreAddress storeAddress;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategory> storeCategories = new ArrayList<>();

    @Builder
    public Store(LocalDateTime created_at, String created_by,
                 LocalDateTime updated_at, String updated_by,
                 LocalDateTime deleted_at, String deleted_by,
                 String store_name, String phone_number, StoreAddress storeAddress,
                 Long store_id, List<StoreCategory> storeCategories) {
        this.store_id = store_id;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
        this.deleted_at = deleted_at;
        this.deleted_by = deleted_by;
        this.store_name = store_name;
        this.phone_number = phone_number;
        this.storeAddress = storeAddress;
        this.storeCategories = storeCategories;
    }
}
