package com.example.demo.controllers;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
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
       User newUser = new User();
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
