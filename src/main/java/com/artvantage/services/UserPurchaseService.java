package com.artvantage.services;

//import com.artvantage.entity.UserPurchase;
import com.artvantage.repositories.UserPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPurchaseService {
    private final UserPurchaseRepository userPurchaseRepository;

    public UserPurchaseService(UserPurchaseRepository userPurchaseRepository) {
        this.userPurchaseRepository = userPurchaseRepository;
    }

}


