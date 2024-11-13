package com.sparta.harmony.store.entity;

import com.sparta.harmony.order.entity.Timestamped;
import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.user.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID storeId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String phoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategory> storeCategories = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    @Builder
    public Store(String storeName, String phoneNumber, Address address,
                 UUID storeId, List<StoreCategory> storeCategories,List<Review> reviews) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.storeCategories = storeCategories;
        this.reviews = reviews;
    }

    public void addCategories(List<StoreCategory> storeCategories) {
        this.storeCategories.addAll(storeCategories);
    }

    public void updateStoreInfo(String storeName, String phoneNumber, Address address) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void clearCategories() {  // StoreCategory 업데이트에 사용 (기존 StoreCategory 목록 초기화)
        this.storeCategories.clear();
    }
}
