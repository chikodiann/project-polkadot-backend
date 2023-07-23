package com.artvantage.entity;

import com.artvantage.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "contents")
public abstract class Content {

    @Id
    @Column(name = "content_id")
    private String contentId;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int price; // Add the price attribute to store the content price

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String contentDescription;

    @Column(nullable = false)
    private String contentCreator; // Take the wallet address of the creator upon upload

    private boolean purchased;
    private String transactionDetails;
//
//    @OneToMany(mappedBy = "content")
//    private List<UserPurchase> purchases;

    public abstract Object getSpecificAttribute();
    public abstract void setSpecificAttribute(String specificAttribute);


}
