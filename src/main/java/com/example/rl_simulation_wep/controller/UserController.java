package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.config.JwtTokenUtil;
import com.example.rl_simulation_wep.dto.UserDTO;
import com.example.rl_simulation_wep.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{userId}")
    @Operation(summary = "특정 사용자 조회")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "이름/프사/성별/한줄소개/점수 변경",
            description = "JWT token 을 header로 전달해야 함(인증 필요)",
            parameters = {
                    @Parameter(name = "Authorization", description = "value 예시) Bearer 8Kvpdkbihakvis", required = true, in = ParameterIn.HEADER)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 업데이트 성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "403", description = "수정할 자격이 없는 토큰 (Forbidden)", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음 (Not Found)", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long authenticatedUserId = Long.valueOf(JwtTokenUtil.extractUserId(token));

        // 요청된 userId와 로그인한 사용자의 ID가 같은지 확인
        if (!authenticatedUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 403 Forbidden
        }

        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found

        }
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 탈퇴",
            description = "JWT token 을 header로 전달해야 함(인증 필요)",
            parameters = {
                    @Parameter(name = "Authorization", description = "value 예시) Bearer 8Kvpdkbihakvis", required = true, in = ParameterIn.HEADER)
            }
    )
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean isDeleted = userService.deleteUser(userId);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}

