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
    @Column(name = "wallet_address")
    private String walletAddress;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Purchase> userPurchases;



    // Custom constructor
    public User(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    // No-args constructor
    public User() {
    }
}

