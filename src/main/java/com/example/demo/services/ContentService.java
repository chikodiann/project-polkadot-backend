package com.example.demo.services;

import com.example.demo.entity.Content;
import com.example.demo.entity.User;
import com.example.demo.entity.UserPurchase;
import com.example.demo.enums.ContentType;
import com.example.demo.exceptions.ContentNotFoundException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PaymentFailedException;
import com.example.demo.repositories.ContentRepository;
import com.example.demo.repositories.UserPurchaseRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final BlockchainServiceImpl blockchainService;
    private final UserPurchaseRepository userPurchaseRepository;


    public boolean purchaseContent(PaymentRequest paymentRequest, boolean processOnBlockchain) {
        // Check if the content exists in the database.
        Content content = contentRepository.findById(paymentRequest.getContentId()).orElse(null);

        if (content == null) {
            throw new NotFoundException("Content not found.");
        }

        if (processOnBlockchain) {
            // Perform blockchain payment and get the payment status
            boolean isPaymentSuccessful = blockchainService.makePayment(paymentRequest);

            if (!isPaymentSuccessful) {
                throw new PaymentFailedException("Payment was unsuccessful.");
            }
        }

        // Mark the content as purchased and set the transaction details.
        content.setPurchased(true);
        content.setTransactionDetails(paymentRequest.getTransactionDetails());

        // Save the updated content in the database.
        contentRepository.save(content);

        // Add the user purchase record to the database
        User user = userRepository.findById(paymentRequest.getCreatorAddress())
                .orElseThrow(() -> new NotFoundException("User not found."));
        UserPurchase userPurchase = UserPurchase.builder()
                .user(user)
                .content(content)
                .purchaseDate(LocalDateTime.now())
                .build();
        userPurchaseRepository.save(userPurchase);

        return true;
    }

    // ... Other methods ...

    public List<Content> getAllContents() {

        return contentRepository.findAll();
    }
    public List<Content> getContentsByContentCategory(ContentType contentType) {
        return contentRepository.findByContentType(contentType);
    }

    public Content getContentById(String contentId) {
        return contentRepository.findById(contentId).orElse(null);
    }

    public Content purchaseContent(String contentId, String userId, String transactionDetails) throws ContentNotFoundException {
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null) {
            // Assuming the payment is successful on the blockchain,
            // set the content's purchase status and transaction details.
            content.setPurchased(true);
            content.setTransactionDetails(transactionDetails);

            // Save the updated content in the database.
            contentRepository.save(content);

            // Add the user purchase record to the database
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
            UserPurchase userPurchase = UserPurchase.builder()
                    .user(user)
                    .content(content)
                    .purchaseDate(LocalDateTime.now())
                    .build();
            userPurchaseRepository.save(userPurchase);

            return content;
        } else {
            throw new ContentNotFoundException("Content not found with ID: " + contentId);
        }
    }

    public Content purchaseContent(PaymentRequest paymentRequest, boolean processOnBlockchain) throws ContentNotFoundException {
        // Check if the content exists in the database.
        Content content = contentRepository.findById(paymentRequest.getContentId()).orElse(null);

        if (content == null) {
            throw new ContentNotFoundException("Content not found with ID: " + paymentRequest.getContentId());
        }

        if (processOnBlockchain) {
            try {
                // Perform blockchain payment and get the payment status
                boolean isPaymentSuccessful = blockchainService.makePayment(paymentRequest);

                if (!isPaymentSuccessful) {
                    throw new PaymentFailedException("Payment was unsuccessful.");
                }
            } catch (PaymentFailedException e) {
                throw e; // Re-throw the PaymentFailedException
            }
        // Assuming the payment process is successful on the blockchain,
        // set the content's purchase status and transaction details.

        return content;
    }

}

}
