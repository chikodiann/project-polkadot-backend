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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ContentService.class);

    public boolean purchaseContent(PaymentRequest paymentRequest, boolean processOnBlockchain) throws ContentNotFoundException {
        // Check if the content exists in the database.
        Content content = contentRepository.findById(paymentRequest.getContentId()).orElse(null);

        if (content == null) {
            throw new ContentNotFoundException("Content not found with ID: " + paymentRequest.getContentId());
        }

        // Log the start of the payment process.
        logger.info("Starting payment process for content with ID: {}", paymentRequest.getContentId());

        if (processOnBlockchain) {
            try {
                // Perform blockchain payment and get the payment status
                boolean isPaymentSuccessful = blockchainService.makePayment(paymentRequest);

                if (!isPaymentSuccessful) {
                    throw new PaymentFailedException("Payment was unsuccessful.");
                }
            } catch (PaymentFailedException e) {
                // Log the payment failure.
                logger.error("Payment failed for content with ID: {}", paymentRequest.getContentId());

                // Re-throw the PaymentFailedException
                throw e;
            }
        }

        // Log the successful payment process.
        logger.info("Payment process for content with ID: {} was successful", paymentRequest.getContentId());

        // Assuming the payment process is successful on the blockchain,
        // set the content's purchase status and transaction details.
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

}
