package com.sparta.harmony.user.dto;

import com.sparta.harmony.user.entity.Address;
import com.sparta.harmony.user.entity.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username must contain only lowercase letters and numbers.")
    private String userName;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
            message = "Password must contain uppercase, lowercase, number, and special character.")
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotNull(message = "Role is required.")
    private Role role;

    @NotNull(message = "Address is required.")
    private Address address;
}
