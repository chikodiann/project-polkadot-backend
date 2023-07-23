package com.artvantage.services;

import com.artvantage.DTO.ContentItemDto;
import com.artvantage.DTO.SearchResultsDto;
import com.artvantage.entity.*;
import com.artvantage.exceptions.ContentNotFoundException;
import com.artvantage.exceptions.PaymentFailedException;
import com.artvantage.repositories.ContentRepository;
import com.artvantage.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final BlockchainServiceImpl blockchainService;
    private boolean processOnBlockchain;

    public void saveContent(Content content) {
        contentRepository.save(content);
    }

    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    public void deleteContentById(String contentId) {
        contentRepository.deleteById(Long.valueOf(contentId));
    }
    // Method to save video content
    public void saveVideoContent(Video video) {
        // Save the video content in the database using the contentRepository
        contentRepository.save(video);
    }

    // Method to update video content

    public PDF getPdfContentById(String contentId) {
        Content content = getContentById(contentId);
        if (content instanceof PDF) {
            return (PDF) content;
        } else {
            // If the content is not a PDF, return null or handle the error as needed
            return null;
        }
    }

    public void savePdfContent(PDF pdfContent) {
        saveContent(pdfContent);
    }

    public Image getImageContentById(String contentId) {
        Content content = getContentById(contentId);
        if (content instanceof Image) {
            return (Image) content;
        } else {
            // If the content is not an Image, return null or handle the error as needed
            return null;
        }
    }

    public void saveImageContent(Image imageContent) {
        saveContent(imageContent);
    }

    public Audio getAudioContentById(String contentId) {
        Content content = getContentById(contentId);
        if (content instanceof Audio) {
            return (Audio) content;
        } else {
            // If the content is not an Audio, return null or handle the error as needed
            return null;
        }
    }

    public void saveAudioContent(Audio audioContent) {
        saveContent(audioContent);
    }

    public boolean purchaseContent(PaymentRequest paymentRequest) throws ContentNotFoundException, PaymentFailedException {
        // Check if the content exists in the database.
        Optional<Content> optionalContent = contentRepository.findById(Long.valueOf(paymentRequest.getContentId()));
        Content content = optionalContent.orElseThrow(() -> new ContentNotFoundException("Content not found with ID: " + paymentRequest.getContentId()));

        // Log the start of the payment process.
        System.out.println("Starting payment process for content with ID: " + paymentRequest.getContentId());

        boolean isPaymentSuccessful = false;

        if (processOnBlockchain) {
            try {
                // Perform blockchain payment and get the payment status
                isPaymentSuccessful = blockchainService.makePayment(paymentRequest);

                if (!isPaymentSuccessful) {
                    throw new PaymentFailedException("Payment was unsuccessful.");
                }
            } catch (PaymentFailedException e) {
                // Log the payment failure.
                System.out.println("Payment failed for content with ID: " + paymentRequest.getContentId());

                // Re-throw the PaymentFailedException
                throw e;
            }
        } else {
            isPaymentSuccessful = true;
        }

        if (isPaymentSuccessful) {
            // Log the successful payment process.
            System.out.println("Payment process for content with ID: " + paymentRequest.getContentId() + " was successful");

            // Assuming the payment process is successful on the blockchain,
            // set the content's purchase status and transaction details.
            content.setPurchased(true);
            content.setTransactionDetails(paymentRequest.getTransactionDetails());

            // Save the updated content in the database.
            contentRepository.save(content);

            return true;
        } else {
            // Handle the case when the payment is not successful
            // Here, we'll return false to indicate payment failure.
            return false;
        }
    }
    public Content getContentById(String contentId) {
        Optional<Content> optionalContent = contentRepository.findById(Long.valueOf(contentId));
        return optionalContent.orElse(null);
    }
    public SearchResultsDto searchContentItems() {
        List<Long> contentIds = new ArrayList<>();
        contentIds.add(1L); // Add content IDs as needed

        // Retrieve the contents by their IDs
        List<Content> searchResultsList = getContentListByIds(contentIds);

        // Map the search results to ContentItemDto list
        List<ContentItemDto> contentItemDtoList = mapContentListToContentItemDtoList(searchResultsList);

        // Create and populate the response payload
        SearchResultsDto searchResults = new SearchResultsDto();
        searchResults.setResults(contentItemDtoList);

        return searchResults;
    }

    private List<Content> getContentListByIds(List<Long> contentIds) {
        return contentRepository.findAllById(contentIds);
    }


    // Helper method to map Content list to ContentItemDto list
    private List<ContentItemDto> mapContentListToContentItemDtoList(List<Content> contentList) {
        List<ContentItemDto> contentItemDtoList = new ArrayList<>();
        for (Content content : contentList) {
            ContentItemDto contentItemDto = new ContentItemDto();
            contentItemDto.setContentId(String.valueOf(content.getContentId()));
            contentItemDto.setContentType(content.getContentType());
            contentItemDto.setContentDescription(content.getContentDescription());
            contentItemDto.setPurchased(content.isPurchased());
            contentItemDto.setTransactionDetails(content.getTransactionDetails());
            contentItemDtoList.add(contentItemDto);
        }
        return contentItemDtoList;
    }

    public boolean processPaymentNotification() {
        return false;
    }

    public Video getVideoContentById(String contentId) throws ContentNotFoundException {
        Optional<Content> optionalContent = contentRepository.findById(Long.valueOf(contentId));
        if (optionalContent.isPresent() && optionalContent.get() instanceof Video) {
            return (Video) optionalContent.get();
        } else {
            // Handle the case where the content with the given ID is not a Video
            // You can throw an exception, return null, or handle it based on your application's requirements
            // For example, throwing an exception would be appropriate to indicate the ID does not correspond to a Video content.
            throw new ContentNotFoundException("Video not found with ID: " + contentId);
        }
    }

}
