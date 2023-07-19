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
public class CategoryDTO {
    private String contentCategoryId;
    private ContentType contentType;
}
