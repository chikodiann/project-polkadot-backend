package com.artvantage.repositories;

import com.artvantage.entity.UserPurchase;
import com.artvantage.entity.UserPurchaseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, UserPurchaseId> {
    // Method to find all user purchases by userId
    List<UserPurchase> findAllByUserId(String userId);
}

