package com.artvantage.controllers;

import com.artvantage.exceptions.PaymentFailedException;
import com.artvantage.exceptions.UnauthorizedException;
import com.artvantage.services.ContentService;
import com.artvantage.exceptions.ContentNotFoundException;
import com.artvantage.exceptions.NotFoundException;
import com.artvantage.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private ContentService contentService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseContent(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Trigger the payment process and get the payment status from the blockchain
            boolean isPaymentSuccessful = contentService.purchaseContent(paymentRequest);

            // Return the payment status to the frontend
            if (isPaymentSuccessful) {
                return ResponseEntity.ok("Payment successful! Content purchased.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment unsuccessful.");
            }
        } catch (NotFoundException | UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PaymentFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (ContentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}