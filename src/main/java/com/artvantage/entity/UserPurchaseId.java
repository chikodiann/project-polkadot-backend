package com.artvantage.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserPurchaseId implements Serializable {

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "user_id")
    private String userId;
}
