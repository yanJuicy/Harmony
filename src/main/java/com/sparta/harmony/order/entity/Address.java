package com.sparta.harmony.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "detail_address")
    private String detailAddress;

    public Address(String postcode, String address, String detailAddress) {
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }

}
