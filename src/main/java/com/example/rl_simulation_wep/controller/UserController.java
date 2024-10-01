package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.dto.UserCreationRequest;
import com.example.rl_simulation_wep.dto.UserDTO;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        // DTO와 비밀번호를 분리하여 받음
        UserDTO userDTO = userCreationRequest.getUserDTO();
        String rawPassword = userCreationRequest.getPassword();

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("이메일이 이미 존재합니다."); // 이메일 중복 시 400 Bad Request 응답
        }

        // 서비스 계층에서 비밀번호 처리
        UserDTO createdUser = userService.createUser(userDTO, rawPassword);
        return ResponseEntity.ok(createdUser); // 회원가입 성공 시 UserDTO 반환
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
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
