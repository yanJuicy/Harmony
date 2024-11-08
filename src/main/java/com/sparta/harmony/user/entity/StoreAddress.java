package com.sparta.harmony.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class StoreAddress {

    private String addrUUID;
    private String postcode;
    private String address;
    private String detailAddress;

    @Builder
    public StoreAddress(String postcode, String address, String detailAddress) {
        this.addrUUID = UUID.randomUUID().toString();
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
