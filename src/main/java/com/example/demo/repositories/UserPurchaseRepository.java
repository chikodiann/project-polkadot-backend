package com.example.demo.repositories;

import com.example.demo.entity.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
    //method to find all user purchases by userId
    List<UserPurchase> findAllByUserId(String userId);

    //method to find all user purchases by contentId
    List<UserPurchase> findAllByContentId(String contentId);

}
