package com.artvantage.services;


import com.artvantage.entity.Content;
import com.artvantage.entity.Purchase;
import com.artvantage.entity.User;
import com.artvantage.entity.UserContentAccess;
import com.artvantage.exceptions.ContentNotFoundException;
import com.artvantage.exceptions.UserNotFoundException;
import com.artvantage.repositories.ContentRepository;
import com.artvantage.repositories.PurchaseRepository;
import com.artvantage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public Purchase purchaseContent(String userId, String contentId, String transactionDetails) throws ContentNotFoundException {
        // Check if the user and content exist in the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException("Content not found with ID: " + contentId));

        // Handle content purchase validation, e.g., check if the user has sufficient funds.

        // Create a new purchase entry the content and save it in the database
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setContent(content);
        purchase.setTransactionDetails(transactionDetails);
        purchase.setPurchaseDate(LocalDateTime.now());

        // Set the payment method and status
        purchase.setPaymentMethod("Tranfer tokens");
        purchase.setPaymentStatus("Completed");

        UserContentAccess userContentAccess = new UserContentAccess();
        userContentAccess.setUser(user);
        userContentAccess.setContent(content);
        userContentAccess.setAccessExpiration(null); // Set accessExpiration to null for indefinite access

        purchaseRepository.save(purchase);

        return purchase;
    }

    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
