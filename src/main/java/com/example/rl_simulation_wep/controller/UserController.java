package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.dto.UserCreationRequest;
import com.example.rl_simulation_wep.dto.UserDTO;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "이메일이 이미 존재합니다.")
    })
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        UserDTO userDTO = userCreationRequest.getUserDTO();
        String rawPassword = userCreationRequest.getPassword();

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("이메일이 이미 존재합니다.");
        }

        UserDTO createdUser = userService.createUser(userDTO, rawPassword);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    @Operation(summary = "모든 사용자 점수 내림차순으로 조회(랭킹)")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 사용자 조회")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
