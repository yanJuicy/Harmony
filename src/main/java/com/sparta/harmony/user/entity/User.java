package com.sparta.harmony.user.entity;

import com.sparta.harmony.order.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @Builder
    public User(UUID userId, String password, String userName,
                String email, Role role, Address address) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.address = address;
    }
}
