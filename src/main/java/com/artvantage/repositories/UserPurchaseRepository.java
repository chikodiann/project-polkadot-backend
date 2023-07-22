package com.artvantage.repositories;

import com.artvantage.entity.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, String> {
    //method to find all user purchases by userId
    List<UserPurchase> findAllByUser_WalletAddress(String walletAddress);
    //method to find all user purchases by contentId
    List<UserPurchase> findAllByContent_ContentId(String contentId);

}
