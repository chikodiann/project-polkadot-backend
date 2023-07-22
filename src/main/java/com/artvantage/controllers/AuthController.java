package com.artvantage.controllers;

import com.artvantage.entity.User;
import com.artvantage.repositories.UserRepository;
import com.artvantage.request.LoginRequest;
import com.artvantage.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRepository userRepository;

   @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody SignupRequest request) {
       String walletAddress = request.getWalletAddress();

       // Check if the wallet address already exists in the database
       User existingUser = userRepository.findByWalletAddress(walletAddress);
       if (existingUser != null) {
           return ResponseEntity.badRequest().body("User already exist!");
       }

       // Create a new user entity and set the wallet address
       User newUser = new User(walletAddress);
       newUser.setWalletAddress(walletAddress);

       // Save the new user in the database
       userRepository.save(newUser);

       return ResponseEntity.ok("Signup Successful!");
   }

   @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
       String walletAddress = request.getWalletAddress();

       // Check if the wallet address exists in the database
       User existingUser = userRepository.findByWalletAddress(walletAddress);
       if (existingUser == null){
           return ResponseEntity.badRequest().body("User not found!");
       }
       return ResponseEntity.ok("Login Successful!");
   }
}
