package com.artvantage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UploadedFileDto {
    private String id;
    private String filename;
    private String contentType;
    private long size;


    public UploadedFileDto(String s) {

    }
}
