package com.example.demo.entity;

import com.example.demo.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {
    private String contentCategoryId;
    private ContentType contentType;
}
