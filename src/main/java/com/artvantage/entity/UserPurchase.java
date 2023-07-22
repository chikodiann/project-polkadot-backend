package com.artvantage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@IdClass(UserPurchaseId.class)
@Table(name = "user_purchases")
public class UserPurchase implements Serializable {

    @Id
    @JoinColumn(name = "user_id", referencedColumnName = "walletAddress")
    private String walletAddress;

    @Id
    @JoinColumn(name = "contentId")
    private String purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "walletAddress", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "contentId", insertable = false, updatable = false)
    private Content content;

    private LocalDateTime purchaseDate;
}

