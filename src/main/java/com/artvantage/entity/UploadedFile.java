package com.artvantage.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UploadedFile {
    private String contentId;
    private String filename;
    private String contentType;
    private long size;


}
