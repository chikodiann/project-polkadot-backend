package com.artvantage.services;

import com.artvantage.blockchain.BlockchainService;
import com.artvantage.request.PaymentRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class BlockchainServiceImpl implements BlockchainService {
    private static final String BLOCKCHAIN_API_URL = "https://example-blockchain-api.com/payments";
    //PLACEHOLDER to input blockchain payment API
    private RestTemplate restTemplate;

    @Override
    public boolean makePayment(PaymentRequest paymentRequest) {
        // Assuming paymentRequest contains the necessary data for the blockchain transaction (e.g., contentId, price, creatorAddress)
        // Construct the request to the blockchain API
        // For simplicity, let's assume the API requires contentId, price, and creatorAddress as query parameters
        String apiUrl = BLOCKCHAIN_API_URL + "?contentId=" + paymentRequest.getContentId()
                + "&price=" + paymentRequest.getPrice()
                + "&creatorAddress=" + paymentRequest.getCreatorAddress();

        // Send the request to the blockchain API
        // For this example, we are assuming the blockchain API responds with a boolean indicating payment success
        Boolean paymentSuccess = restTemplate.getForObject(apiUrl, Boolean.class);

        return paymentSuccess != null && paymentSuccess;
    }


}
