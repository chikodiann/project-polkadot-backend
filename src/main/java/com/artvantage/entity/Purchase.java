package com.artvantage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(nullable = false)
    private String transactionDetails; // This should be the transaction hash on the blockchain

    @Column(nullable = false)
    private LocalDateTime purchaseDate; // The date and time of the purchase

    @Column(nullable = false)
    private String paymentMethod; // via token transfer

    @Column(nullable = false)
    private String paymentStatus;
}
