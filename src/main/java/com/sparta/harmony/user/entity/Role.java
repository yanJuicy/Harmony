package com.sparta.harmony.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER","일반 사용자"),
    OWNER("ROLE_OWNER","음식점 사장"),
    MANAGER("ROLE_MANAGER","관리자"),
    MASTER("ROLE_MASTER","총책임자");

    private final String key;
    private final String title;
}
