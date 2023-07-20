package com.example.demo.entity;

import com.example.demo.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @OneToMany(mappedBy = "content")
    private List<UserPurchase> purchases;

}
