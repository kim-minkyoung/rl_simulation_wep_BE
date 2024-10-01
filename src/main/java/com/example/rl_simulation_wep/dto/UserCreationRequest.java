package com.example.rl_simulation_wep.dto;

public class UserCreationRequest {
    private UserDTO userDTO;
    private String password;

    // Getters and Setters
    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
