package com.artvantage.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseDTO {
    private String purchaseId;
    private  String userId;
    private String contentId;
    private  String transactionDetails;
    private LocalDateTime purchaseDate;
}
