package com.artvantage.DTO;

import com.artvantage.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String contentCategoryId;
    private ContentType contentType;
}
