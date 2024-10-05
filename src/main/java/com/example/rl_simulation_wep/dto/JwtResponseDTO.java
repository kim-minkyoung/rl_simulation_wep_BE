package com.example.rl_simulation_wep.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponseDTO {
    private String jwtToken;

    public JwtResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
