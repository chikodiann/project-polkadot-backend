package com.artvantage.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private String walletAddress;
    private String profilePicture;
    private String bio;
    private String SocialMediaLinks;
}
