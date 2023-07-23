package com.artvantage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
@Entity
@AllArgsConstructor
public class UserPurchase implements Serializable {

    @Id
    private String userId;

    public UserPurchase() {
    }

}
