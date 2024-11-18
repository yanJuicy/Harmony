package com.sparta.harmony.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuRequestDto {

    private boolean isAvailable;
    private String name;
    private String descriptionRequest;
    private int price;
    private String imageUrl;

}
