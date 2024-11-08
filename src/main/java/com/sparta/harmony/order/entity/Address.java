package com.sparta.harmony.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Column(length = 6, nullable = false)
    private String postcode;

    @Column(length = 200, nullable = false)
    private String address;

    @Column(length = 200, name = "detail_address", nullable = false)
    private String detailAddress;

    @Builder
    public Address(String postcode, String address, String detailAddress) {
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
