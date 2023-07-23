package com.artvantage.controllers;

import com.artvantage.exceptions.PaymentFailedException;
import com.artvantage.exceptions.UnauthorizedException;
import com.artvantage.services.ContentService;
import com.artvantage.exceptions.ContentNotFoundException;
import com.artvantage.exceptions.NotFoundException;
import com.artvantage.request.PaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment Controller", description = "This provides endpoints for making payments and purchasing content.")
public class PaymentController {
    private final ContentService contentService;

    @Operation(summary = "Purchases content with payment", responses = {
            @ApiResponse(responseCode = "200", description = "Payment successful, content purchased",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Payment unsuccessful",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Payment failed",
                    content = @Content(mediaType = "text/plain"))
    })

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseContent(@Parameter(description = "The payment request information", required = true)
                                                  @RequestBody PaymentRequest paymentRequest) {
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

    @Operation(summary = "Handle payment notification", description = "Handles payment notification from frontend/blockchain.")
    @GetMapping("/payment-notification")
    public ResponseEntity<String> handlePaymentNotification(
            @Parameter(description = "The unique transaction ID", required = true) @RequestParam("transactionId") String transactionId,
            @Parameter(description = "The payment amount", required = true) @RequestParam("amount") double amount,
            @Parameter(description = "The payment method used", required = true) @RequestParam("paymentMethod") String paymentMethod
    ) {
        // Process the payment notification and update the backend to approve the purchase
        try {
            boolean isPaymentSuccessful = contentService.processPaymentNotification();

            if (isPaymentSuccessful) {
                // Return a success response if the payment was successfully processed
                return ResponseEntity.ok("Payment notification processed successfully. Purchase approved.");
            } else {
                // Return an error response if the payment was not successful
                return ResponseEntity.badRequest().body("Payment notification processing failed.");
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during payment notification processing
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment notification.");
        }
    }
}
