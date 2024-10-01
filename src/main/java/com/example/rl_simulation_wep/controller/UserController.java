package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.dto.UserCreationRequest;
import com.example.rl_simulation_wep.dto.UserDTO;
import com.example.rl_simulation_wep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        // DTO와 비밀번호를 분리하여 받음
        UserDTO userDTO = userCreationRequest.getUserDTO();
        String rawPassword = userCreationRequest.getPassword();

        // 서비스 계층에서 비밀번호 처리
        UserDTO createdUser = userService.createUser(userDTO, rawPassword);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
