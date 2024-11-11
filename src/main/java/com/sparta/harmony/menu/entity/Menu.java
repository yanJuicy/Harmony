package com.sparta.harmony.menu.entity;

import com.sparta.harmony.order.entity.Timestamped;
import com.sparta.harmony.store.entity.Store;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID menuId;

    private String name;

    private String description;

    private int price;

    private String imageUrl;

    private boolean isAvailable;

    private boolean isHidden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Menu(String name, String description,
                int price, String imageUrl, boolean isAvailable,
                boolean isHidden, Store store) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
        this.isHidden = isHidden;
        this.store = store;
    }

}
