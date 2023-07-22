package com.artvantage.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserPurchaseId implements Serializable {
    private String walletAddress;
    private String purchaseId;
}
