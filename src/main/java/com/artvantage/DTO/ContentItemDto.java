package com.artvantage.DTO;

import com.artvantage.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContentItemDto {
    private String contentId;
    private ContentType contentType;
    private String content;
    private int price;
    private String filePath;
    private String contentDescription;
    private String contentCreator;
    private boolean purchased;
    private String transactionDetails;

}

