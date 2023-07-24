package com.artvantage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class UserPurchaseId implements Serializable {

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "user_id")
    private String userId;

}
