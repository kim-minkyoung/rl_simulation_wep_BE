package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.dto.UserDTO;
import com.example.rl_simulation_wep.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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

//description = "JWT token 을 header로 전달해야 함(인증 필요)",
//parameters = {
//@Parameter(name = "Authorization", description = "value 예시) Bearer 8Kvpdkbihakvis", required = true, in = ParameterIn.HEADER)
//            }
