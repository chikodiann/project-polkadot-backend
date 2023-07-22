package com.artvantage.request;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class PaymentRequest {
    private String contentId;
    private int price;
    private String creatorAddress;
    private String transactionDetails;

}
