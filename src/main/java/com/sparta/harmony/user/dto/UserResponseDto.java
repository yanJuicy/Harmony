package com.sparta.harmony.user.dto;

import com.sparta.harmony.user.entity.Address;
import com.sparta.harmony.user.entity.Role;
import com.sparta.harmony.user.entity.User;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserResponseDto {
    private UUID userId;
    private String userName;
    private String email;
    private Role role;
    private Address address;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.address = user.getAddress();
    }
}
