package com.sparta.harmony.store.entity;

import com.sparta.harmony.order.entity.Timestamped;
import com.sparta.harmony.user.entity.Address;
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
public class Store extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID store_id;

    @Column(nullable = false)
    private String store_name;

    @Column(nullable = false)
    private String phone_number;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategory> storeCategories = new ArrayList<>();

    @Builder
    public Store(String store_name, String phone_number, Address address,
                 UUID store_id, List<StoreCategory> storeCategories) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.phone_number = phone_number;
        this.address = address;
        this.storeCategories = storeCategories;
    }
}
