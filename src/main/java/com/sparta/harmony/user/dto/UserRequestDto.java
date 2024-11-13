package com.sparta.harmony.user.dto;

import com.sparta.harmony.user.entity.Address;
import com.sparta.harmony.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String password;
    private String userName;
    private String email;
    private Role role;
    private Address address;
}
