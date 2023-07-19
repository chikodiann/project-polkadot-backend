package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String purchaseId;
    private  String userId;
    private String contentId;
    private  String transactionDetails; //this should be transaction hash on the blockchain
    private LocalDateTime purchaseDate; // The date and time of the purchase
}
