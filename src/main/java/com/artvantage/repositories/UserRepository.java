package com.artvantage.repositories;

import com.artvantage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByWalletAddress(String walletAddress);
}
