package com.sparta.harmony.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime updated_at;
    private String updated_by;
    private LocalDateTime deleted_at;
    private String deleted_by;

    @ColumnDefault("false")
    private Boolean deleted; //true : 삭제

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
    private UserAddress userAddress;

    @Builder
    public User(LocalDateTime created_at, String created_by,
                LocalDateTime updated_at, String updated_by,
                LocalDateTime deleted_at, String deleted_by, Boolean deleted,
                Long user_id, String password, String user_name,
                String email, Role role, UserAddress userAddress) {
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
        this.deleted_at = deleted_at;
        this.deleted_by = deleted_by;
        this.deleted = deleted;
        this.user_id = user_id;
        this.password = password;
        this.user_name = user_name;
        this.email = email;
        this.role = role;
        this.userAddress = userAddress;
    }
}
