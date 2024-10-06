package com.example.rl_simulation_wep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String userName;
    private String userImage;

    @Enumerated(EnumType.STRING)
    private Gender userGender;

    private LocalDate birthDate;
    private String userBio;
    private int userScore = 10000;

    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime userCreatedAt = LocalDateTime.now();

    @Column(name = "user_updated_at")
    private LocalDateTime userUpdatedAt = LocalDateTime.now();

    @Column(name = "user_last_login")
    private LocalDateTime userLastLogin = LocalDateTime.now();

}
