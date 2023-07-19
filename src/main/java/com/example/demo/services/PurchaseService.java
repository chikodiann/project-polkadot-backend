package com.example.demo.services;


import com.example.demo.entity.Purchase;
import com.example.demo.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
