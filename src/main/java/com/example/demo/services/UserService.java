package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(String walletAddress) {
        // Check if the user is already registered
        if (userRepository.findByWalletAddress(walletAddress) != null) {
            throw new UserAlreadyExistsException("User already exists with wallet address: " + walletAddress);
        }

        // Create a new user and save it in the database
        User user = new User();
        user.setWalletAddress(walletAddress);
        // Set other user attributes if needed
        userRepository.save(user);

        return user;
    }
}

