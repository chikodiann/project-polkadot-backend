package com.artvantage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String purchaseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;
    @Column(nullable = false)
    private  String transactionDetails; //this should be transaction hash on the blockchain
    @Column(nullable = false)
    private LocalDateTime purchaseDate;
    // The date and time of the purchase
    @Column(nullable = false)
    private String paymentMethod; //via token transfer
    @Column(nullable = false)
    private String paymentStatus;

}
