package com.artvantage.services;

import com.artvantage.entity.User;
import com.artvantage.exceptions.UserAlreadyExistsException;
import com.artvantage.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(String walletAddress) {
        // Check if the user is already registered
        if (userRepository.findByWalletAddress(walletAddress) != null) {
            throw new UserAlreadyExistsException("User already exists with wallet address: " + walletAddress);
        }

        // Create a new user and save it in the database
        User user = new User(walletAddress);
        user.setWalletAddress(walletAddress);

        // Save the user in the database
        userRepository.save(user);

        return user;
    }
}

