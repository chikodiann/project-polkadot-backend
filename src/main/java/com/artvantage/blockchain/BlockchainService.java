package com.artvantage.blockchain;

import com.artvantage.request.PaymentRequest;

public interface BlockchainService {
    boolean makePayment(PaymentRequest paymentRequest);

}
