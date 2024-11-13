package com.sparta.harmony.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuCreateRequestDto {

    private boolean isAvailable;
    private String name;
    private String description;
    private int price;
    private String imageUrl;

}
