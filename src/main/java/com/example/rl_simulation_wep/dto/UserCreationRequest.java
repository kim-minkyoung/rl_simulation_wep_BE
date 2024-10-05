package com.example.rl_simulation_wep.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreationRequest {
    private UserDTO userDTO;
    private String password;

}
