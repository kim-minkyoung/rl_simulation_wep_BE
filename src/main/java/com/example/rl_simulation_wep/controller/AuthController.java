package com.example.rl_simulation_wep.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API for user authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/perform_login")
    @Operation(summary = "User login", description = "Authenticates a user with their email and password.")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return ResponseEntity.ok("로그인 성공"); // 로그인 성공 메시지
    }

    @PostMapping("/perform_logout")
    @Operation(summary = "User logout", description = "Logs out the currently authenticated user.")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 성공"); // 로그아웃 성공 메시지
    }
}
