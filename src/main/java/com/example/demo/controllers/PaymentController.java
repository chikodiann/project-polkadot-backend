package com.example.demo.controllers;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PaymentFailedException;
import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.request.PaymentRequest;
import com.example.demo.services.ContentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private  ContentService contentService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseContent(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Trigger the payment process and get the payment status from the blockchain
            boolean isPaymentSuccessful = contentService.purchaseContent(paymentRequest);

            // Return the payment status to the frontend
            if (isPaymentSuccessful) {
                return ResponseEntity.ok("Payment successful!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment unsuccessful.");
            }
        } catch (NotFoundException | UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PaymentFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
