package com.example.demo.blockchain;

import com.example.demo.request.PaymentRequest;

public interface BlockchainService {
    boolean makePayment(PaymentRequest paymentRequest);

}
