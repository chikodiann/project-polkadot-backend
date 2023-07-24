package com.artvantage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserPurchaseId.class) // Add this annotation to indicate the composite key class
public class UserPurchase {

    @Id
    private String userId;

    @Id
    private String contentId;

    // Add other attributes and relationships as needed
    // ...
}
