package com.example.rl_simulation_wep.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserDTO {
    private Long userId;
    private String email;
    private String userName;
    private String userImage;
    private String userGender;
    private LocalDate birthDate;
    private String userBio;
    private Integer userScore;

}
