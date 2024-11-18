package com.sparta.harmony.user.entity;

import com.sparta.harmony.order.entity.Order;
import com.sparta.harmony.order.entity.Payments;
import com.sparta.harmony.order.entity.Timestamped;
import com.sparta.harmony.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_users")
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payments> paymentsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public void addOrder(Order order) {
        orderList.add(order);
        order.updateUser(this);
    }

    public void addPayments(Payments payments) {
        paymentsList.add(payments);
        payments.updateUser(this);
    }

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

    public void updateUserInfo(String userName, String password, Role role, Address address) {
        if (userName != null && !userName.isBlank()) {
            this.userName = userName;
        }
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
        if (role != null) {
            this.role = role;
        }
        if (address != null) {
            this.address = address;
        }
    }
}
