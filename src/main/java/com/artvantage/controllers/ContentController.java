package com.artvantage.controllers;

import com.artvantage.DTO.ContentItemDto;
import com.artvantage.DTO.SearchResultsDto;
import com.artvantage.DTO.UploadedFileDto;
import com.artvantage.services.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private final ContentService contentService;

    // Upload Endpoint
    @PostMapping("/upload")
    public ResponseEntity<UploadedFileDto> uploadFile(@RequestParam("file") MultipartFile file) {
        // Process the uploaded file and save it
        // For example, store the file in a file system or database and generate an ID

        // Create and populate the response payload
        UploadedFileDto uploadedFile = new UploadedFileDto();
        uploadedFile.setId("file123"); // Replace with the actual ID generated
        uploadedFile.setFilename(file.getOriginalFilename());
        uploadedFile.setContentType(file.getContentType());
        uploadedFile.setSize(file.getSize());

        // Return the response with the uploaded file details
        return ResponseEntity.ok(uploadedFile);
    }

        // List Endpoint
        @GetMapping("/list")
        public ResponseEntity<List<UploadedFileDto>> listFiles() {
            // Retrieve a list of uploaded files
            // For example, fetch the files from a database or file system

            // Create and populate the response payload
            List<UploadedFileDto> uploadedFiles = new ArrayList<>();

            UploadedFileDto file1 = new UploadedFileDto();
            file1.setId("file123"); // Replace with the actual ID of the file
            file1.setFilename("example.jpg");
            file1.setContentType("image/jpeg");
            file1.setSize(1024);

            UploadedFileDto file2 = new UploadedFileDto();
            file2.setId("file456"); // Replace with the actual ID of the file
            file2.setFilename("example.txt");
            file2.setContentType("text/plain");
            file2.setSize(512);

            uploadedFiles.add(file1);
            uploadedFiles.add(file2);

            // Return the list of uploaded files
            return ResponseEntity.ok(uploadedFiles);
        }


    // Search Endpoint
    @GetMapping("/search")
    public ResponseEntity<SearchResultsDto> searchStream(@RequestParam("stream_id") String streamId,
                                                         @RequestParam(value = "query", required = false) String query) {
        // Perform the search in the stream
        List<ContentItemDto> searchResultsList = contentService.searchContentItems(streamId, query).getResults();

        // Create and populate the response payload
        SearchResultsDto searchResults = new SearchResultsDto();
        searchResults.setResults(searchResultsList);

        // Return the search results
        return ResponseEntity.ok(searchResults);
    }
}


