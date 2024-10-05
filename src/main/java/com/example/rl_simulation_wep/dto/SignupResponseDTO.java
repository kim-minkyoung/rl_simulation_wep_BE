package com.example.rl_simulation_wep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponseDTO {
    private UserDTO user;
    private String token;
}
