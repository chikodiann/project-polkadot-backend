package com.artvantage.controllers;

import com.artvantage.entity.User;
import com.artvantage.repositories.UserRepository;
import com.artvantage.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Authentication Controller", description = "The AuthController provides endpoints for accessing the home page of the application.")
public class AuthController {
    private final UserRepository userRepository;

    @Operation(summary = "Creates an account for a user")
    @PostMapping("/signup")
    @ApiResponse(responseCode = "201", description = "User account created successfully")
    @ApiResponse(responseCode = "400", description = "User already exists", content = @Content(mediaType = "text/plain"))
    public ResponseEntity<String> signupUser(
            @Parameter(description = "The wallet address of the user.", required = true, example = "0x1234567890abcdef")
            @RequestParam("walletAddress") String walletAddress) {
        // Check if the wallet address already exists in the database
        User existingUser = userRepository.findByWalletAddress(walletAddress);
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        // Create a new user entity and set the wallet address
        User newUser = new User(walletAddress);
        newUser.setWalletAddress(walletAddress);
        // Save the new user in the database
        userRepository.save(newUser);
        // Return a 201 CREATED status code
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Login user with wallet address")
    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "text/plain"))
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        String walletAddress = request.getWalletAddress();

        // Check if the wallet address exists in the database
        User existingUser = userRepository.findByWalletAddress(walletAddress);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        return ResponseEntity.ok("Login Successful!");
    }
}
