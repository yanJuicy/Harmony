package com.sparta.harmony.store.dto;

import com.sparta.harmony.user.entity.Address;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class StoreResponseDto {
    private UUID storeId;
    private String storeName;
    private String phoneNumber;
    private Address address;
    private List<String> categoryNames;

    @Builder
    public StoreResponseDto(UUID storeId, String storeName, String phoneNumber, Address address, List<String> categoryNames) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.categoryNames = categoryNames;
    }
}
