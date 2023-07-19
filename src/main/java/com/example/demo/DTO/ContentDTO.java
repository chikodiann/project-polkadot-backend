package com.example.demo.DTO;


import com.example.demo.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContentDTO {
    private String contentId;
    private ContentType contentType;
    private String content;
    private int price;
    private String filePath;
    private String contentDescription;
    private String contentCreator;
}
