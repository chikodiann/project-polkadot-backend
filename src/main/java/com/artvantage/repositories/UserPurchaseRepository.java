package com.artvantage.repositories;

import com.artvantage.entity.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, String> {
    // Method to find all user purchases by userId
    List<UserPurchase> findAllByUserId(String userId);
}
