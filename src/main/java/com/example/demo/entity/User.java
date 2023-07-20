package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true,nullable = false)
    private String walletAddress;

    @OneToMany(mappedBy = "user")
    private List<UserPurchase> purchases;
//    private String profilePicture;
//    private String bio;
//    private String SocialMediaLinks;



}
