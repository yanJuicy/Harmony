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
    private UUID user_id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @Builder
    public User(UUID user_id, String password, String user_name,
                String email, Role role, Address address) {
        this.user_id = user_id;
        this.password = password;
        this.user_name = user_name;
        this.email = email;
        this.role = role;
        this.address = address;
    }
}
