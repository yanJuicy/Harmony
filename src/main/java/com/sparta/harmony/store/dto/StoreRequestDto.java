package com.sparta.harmony.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    private String storeName;
    private String phoneNumber;
    private String address;
    private String detailAddress;
    private String postcode;
    private List<UUID> categoryIds;
}
