package com.example.demo.entity;

import com.example.demo.enums.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String contentId;
    private ContentType contentType;
    private String content;
    private int price;
    private String filePath;
    private String contentDescription;
    private String contentCreator; //take the wallet address of the creator upon upload
}
