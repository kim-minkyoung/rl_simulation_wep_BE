package com.example.rl_simulation_wep.dto;

public class JwtResponseDTO {
    private String jwt;

    public JwtResponseDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
