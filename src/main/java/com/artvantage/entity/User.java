package com.artvantage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "walletAddress", unique = true,nullable = false)
    private String walletAddress;

    @OneToMany(mappedBy = "user")
    private List<UserPurchase> purchases;


    // Custom constructor
    public User(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    // No-args constructor
    public User() {
    }
}

