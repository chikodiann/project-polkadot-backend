package com.artvantage.controllers;

import com.artvantage.DTO.ContentItemDto;
import com.artvantage.DTO.SearchResultsDto;
import com.artvantage.DTO.UploadedFileDto;
import com.artvantage.entity.Audio;
import com.artvantage.entity.Image;
import com.artvantage.entity.PDF;
import com.artvantage.entity.Video;
import com.artvantage.enums.ContentType;
import com.artvantage.exceptions.ContentNotFoundException;
import com.artvantage.services.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Tag(name = "Content API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;
    // Upload Endpoint to handle video, audio, image, and pdf content
    @Operation(summary = "Uploads a file", responses = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadedFileDto.class)))
    })

/// Upload Endpoint to handle video, audio, image, and pdf content
    @PostMapping("/upload")
    public ResponseEntity<UploadedFileDto> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("contentType") ContentType contentType,
            @RequestParam("price") int price,
            @RequestParam("contentDescription") String contentDescription,
            @RequestParam("contentCreator") String contentCreator) {

        Content content;

        switch (contentType) {
            case VIDEO -> {
                Video video = new Video();
                video.setPrice(price);
                video.setContentDescription(contentDescription);
                video.setContentCreator(contentCreator);
                video.setFilePath("file_path_placeholder"); // Replace with actual file path
                video.setPurchased(false);
                video.setTransactionDetails(null); // No transaction details upon upload
                content = (Content) video;
            }
            case AUDIO -> {
                Audio audio = new Audio();
                audio.setPrice(price);
                audio.setContentDescription(contentDescription);
                audio.setContentCreator(contentCreator);
                audio.setFilePath("file_path_placeholder"); // Replace with actual file path
                audio.setPurchased(false);
                audio.setTransactionDetails(null); // No transaction details upon upload
                content = (Content) audio;
            }
            case IMAGE -> {
                Image image = new Image();
                image.setPrice(price);
                image.setContentDescription(contentDescription);
                image.setContentCreator(contentCreator);
                image.setFilePath("file_path_placeholder"); // Replace with actual file path
                image.setPurchased(false);
                image.setTransactionDetails(null); // No transaction details upon upload
                content = (Content) image;
            }
            case PDF -> {
                PDF pdf = new PDF();
                pdf.setPrice(price);
                pdf.setContentDescription(contentDescription);
                pdf.setContentCreator(contentCreator);
                pdf.setFilePath("file_path_placeholder"); // Replace with actual file path
                pdf.setPurchased(false);
                pdf.setTransactionDetails(null); // No transaction details upon upload
                content = (Content) pdf;
            }
            default -> {
                return ResponseEntity.badRequest().body(new UploadedFileDto("Invalid content type."));
            }
        }

        // Save the content in the database
        contentService.saveContent((com.artvantage.entity.Content) content);

        // Create and populate the response payload
        UploadedFileDto uploadedFile = new UploadedFileDto();
        uploadedFile.setId("file123"); // Replace with the actual ID generated
        uploadedFile.setFilename(file.getOriginalFilename());
        uploadedFile.setContentType(file.getContentType());
        uploadedFile.setSize(file.getSize());

        // Return the response with the uploaded file details
        return ResponseEntity.ok(uploadedFile);
    }

    // List Endpoint to return all content items as JSON
    @Operation(summary = "Lists all uploaded files", responses = {
            @ApiResponse(responseCode = "200", description = "List of uploaded files",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Content.class, type = "array")))
    })
    @GetMapping("/list")
    public ResponseEntity<List<com.artvantage.entity.Content>> listFiles() {
        List<com.artvantage.entity.Content> allContent = contentService.getAllContent();
        return ResponseEntity.ok(allContent);
    }

    // Search Endpoint
    @Operation(summary = "Searches for content items", responses = {
            @ApiResponse(responseCode = "200", description = "Search results",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResultsDto.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<SearchResultsDto> searchContent() {
        List<ContentItemDto> searchResultsList = contentService.searchContentItems().getResults();

        // Create and populate the response payload
        SearchResultsDto searchResults = new SearchResultsDto();
        searchResults.setResults(searchResultsList);

        // Return the search results
        return ResponseEntity.ok(searchResults);
    }


    // Update Endpoint
    // Update Endpoint for Video
    @Operation(summary = "Update a video content item", description = "Updates an existing video content item.")
    @PutMapping("/update/video/{contentId}")
    public ResponseEntity<String> updateVideoContentItem(
            @Parameter(description = "The ID of the video content item to update", required = true)
            @PathVariable("contentId") String contentId,
            @RequestBody Video updatedVideoContent) throws ContentNotFoundException {

        // Logic to update the video content item with the provided data
        Video existingVideoContent = contentService.getVideoContentById(contentId);
        if (existingVideoContent == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply the updates from the updatedVideoContent to the existingVideoContent
        existingVideoContent.setVideoSpecificAttribute(updatedVideoContent.getVideoSpecificAttribute());

        // Update the common attributes using the base class setter methods
        existingVideoContent.setPrice(updatedVideoContent.getPrice());
        existingVideoContent.setContentDescription(updatedVideoContent.getContentDescription());
        existingVideoContent.setContentCreator(updatedVideoContent.getContentCreator());

        // Save the updated video content item back to the database
        contentService.saveVideoContent(existingVideoContent);

        // Return a success response
        return ResponseEntity.ok("Video content item updated successfully.");
    }

    // Update Endpoint for Audio
    @Operation(summary = "Update an audio content item", description = "Updates an existing audio content item.")
    @PutMapping("/update/audio/{contentId}")
    public ResponseEntity<String> updateAudioContentItem(
            @Parameter(description = "The ID of the audio content item to update", required = true)
            @PathVariable("contentId") String contentId,
            @RequestBody Audio updatedAudioContent) {

        // Logic to update the audio content item with the provided data
        Audio existingAudioContent = contentService.getAudioContentById(contentId);
        if (existingAudioContent == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply the updates from the updatedAudioContent to the existingAudioContent
        existingAudioContent.setAudioSpecificAttribute(updatedAudioContent.getAudioSpecificAttribute());

        // Update the common attributes using the base class setter methods
        existingAudioContent.setPrice(updatedAudioContent.getPrice());
        existingAudioContent.setContentDescription(updatedAudioContent.getContentDescription());
        existingAudioContent.setContentCreator(updatedAudioContent.getContentCreator());

        // Save the updated audio content item back to the database
        contentService.saveAudioContent(existingAudioContent);

        // Return a success response
        return ResponseEntity.ok("Audio content item updated successfully.");
    }

    // Update Endpoint for Image
    @Operation(summary = "Update an image content item", description = "Updates an existing image content item.")
    @PutMapping("/update/image/{contentId}")
    public ResponseEntity<String> updateImageContentItem(
            @Parameter(description = "The ID of the image content item to update", required = true)
            @PathVariable("contentId") String contentId,
            @RequestBody Image updatedImageContent) {

        // Logic to update the image content item with the provided data
        Image existingImageContent = contentService.getImageContentById(contentId);
        if (existingImageContent == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply the updates from the updatedImageContent to the existingImageContent
        existingImageContent.setImageSpecificAttribute((String) updatedImageContent.getImageSpecificAttribute());

        // Update the common attributes using the base class setter methods
        existingImageContent.setPrice(updatedImageContent.getPrice());
        existingImageContent.setContentDescription(updatedImageContent.getContentDescription());
        existingImageContent.setContentCreator(updatedImageContent.getContentCreator());

        // Save the updated image content item back to the database
        contentService.saveImageContent(existingImageContent);

        // Return a success response
        return ResponseEntity.ok("Image content item updated successfully.");
    }

    // Update Endpoint for PDF
    @Operation(summary = "Update a PDF content item", description = "Updates an existing PDF content item.")
    @PutMapping("/update/pdf/{contentId}")
    public ResponseEntity<String> updatePdfContentItem(
            @Parameter(description = "The ID of the PDF content item to update", required = true)
            @PathVariable("contentId") String contentId,
            @RequestBody PDF updatedPdfContent) {

        // Logic to update the PDF content item with the provided data
        PDF existingPdfContent = contentService.getPdfContentById(contentId);
        if (existingPdfContent == null) {
            return ResponseEntity.notFound().build();
        }

        // Apply the updates from the updatedPdfContent to the existingPdfContent
        existingPdfContent.setPdfSpecificAttribute(updatedPdfContent.getPdfSpecificAttribute());

        // Update the common attributes using the base class setter methods
        existingPdfContent.setPrice(updatedPdfContent.getPrice());
        existingPdfContent.setContentDescription(updatedPdfContent.getContentDescription());
        existingPdfContent.setContentCreator(updatedPdfContent.getContentCreator());

        // Save the updated PDF content item back to the database
        contentService.savePdfContent(existingPdfContent);

        // Return a success response
        return ResponseEntity.ok("PDF content item updated successfully.");
    }


    // Delete Endpoint
    @Operation(summary = "Delete a content item", description = "Deletes an existing content item.")
    @DeleteMapping("/delete/{contentId}")
    public ResponseEntity<String> deleteContentItem(
            @Parameter(description = "The ID of the content item to delete", required = true)
            @PathVariable("contentId") String contentId) {

        // If the content item exists, delete it from the database
        com.artvantage.entity.Content existingContent = contentService.getContentById(contentId);
        if (existingContent == null) {
            return ResponseEntity.notFound().build();
        }

        contentService.deleteContentById(contentId);

        // Return a success response
        return ResponseEntity.ok("Content item deleted successfully.");
    }
}
