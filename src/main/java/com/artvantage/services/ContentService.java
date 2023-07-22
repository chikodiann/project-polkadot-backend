package com.artvantage.services;

import com.artvantage.DTO.ContentItemDto;
import com.artvantage.DTO.SearchResultsDto;
import com.artvantage.DTO.UploadedFileDto;
import com.artvantage.entity.Content;
import com.artvantage.enums.ContentType;
import com.artvantage.exceptions.ContentNotFoundException;
import com.artvantage.exceptions.PaymentFailedException;
import com.artvantage.repositories.ContentRepository;
import com.artvantage.repositories.UserRepository;
import com.artvantage.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final BlockchainServiceImpl blockchainService;
    private boolean processOnBlockchain;

    public boolean purchaseContent(PaymentRequest paymentRequest) throws ContentNotFoundException, PaymentFailedException {
        // Check if the content exists in the database.
        Optional<Content> optionalContent = contentRepository.findById(paymentRequest.getContentId());
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

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public List<Content> getContentsByContentCategory(ContentType contentType) {
        return contentRepository.findByContentType(contentType);
    }

    public Content getContentById(String contentId) {
        Optional<Content> optionalContent = contentRepository.findById(contentId);
        return optionalContent.orElse(null);
    }

    // Service method to handle file upload and saving details
    public UploadedFileDto uploadFile(MultipartFile file) {
        // Process the uploaded file and save it
        // For this example, we'll create a dummy implementation

        // Save the file details to the repository
        UploadedFileDto uploadedFileDto = new UploadedFileDto();
        uploadedFileDto.setId("file123"); // Replace with the actual ID generated
        uploadedFileDto.setFilename(file.getOriginalFilename());
        uploadedFileDto.setContentType(file.getContentType());
        uploadedFileDto.setSize(file.getSize());

        return uploadedFileDto;
    }

    public SearchResultsDto searchContentItems(String streamId, String query) {
        // Perform the search in the stream
        List<Content> searchResultsList = contentRepository.findByStreamIdAndQuery(streamId, query);

        // Map the search results to ContentItemDto list
        List<ContentItemDto> contentItemDtoList = mapContentListToContentItemDtoList(searchResultsList);

        // Create and populate the response payload
        SearchResultsDto searchResults = new SearchResultsDto();
        searchResults.setResults(contentItemDtoList);

        return searchResults;
    }

    // Helper method to map Content list to ContentItemDto list
    private List<ContentItemDto> mapContentListToContentItemDtoList(List<Content> contentList) {
        List<ContentItemDto> contentItemDtoList = new ArrayList<>();
        for (Content content : contentList) {
            ContentItemDto contentItemDto = new ContentItemDto();
            contentItemDto.setContentId(content.getContentId());
            contentItemDto.setContentType(content.getContentType());
            contentItemDto.setContentDescription(content.getContentDescription());
            contentItemDto.setPurchased(content.isPurchased());
            contentItemDto.setTransactionDetails(content.getTransactionDetails());
            contentItemDtoList.add(contentItemDto);
        }
        return contentItemDtoList;
    }

    public boolean processPaymentNotification(String transactionId, double amount, String paymentMethod) {
        return false;
    }
}
