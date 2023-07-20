package com.example.demo.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class PaymentRequest {
    private String contentId;
    private int price;
    private String creatorAddress;
    private String transactionDetails;

}
