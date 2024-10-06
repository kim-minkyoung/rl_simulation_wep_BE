package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.config.JwtTokenUtil;
import com.example.rl_simulation_wep.dto.JwtResponseDTO;
import com.example.rl_simulation_wep.dto.SignupResponseDTO;
import com.example.rl_simulation_wep.dto.UserCreationRequest; // 사용자 생성 요청 DTO
import com.example.rl_simulation_wep.dto.UserDTO; // 사용자 DTO
import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.service.AuthService;
import com.example.rl_simulation_wep.service.UserService; // 사용자 서비스
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService; // 사용자 서비스 추가
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, UserService userService, AuthService authService, JwtTokenUtil jwtTokenUtil1, UserRepository userRepository) {
        this.userService = userService; // 사용자 서비스 초기화
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil1;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SignupResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "로그인 아이디/비밀번호 오류", content = @Content(mediaType = ""))
    })
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmail(email);
        String token = authService.login(user.getUserId(), password).getJwtToken();

        UserDTO userDTO = userService.convertToDTO(user);
        Map<String, Object> response = new HashMap<>();
        response.put("userDTO", userDTO);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    @Operation(description = "필독!!! userID 빼고 email부터 보내면 됨")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignupResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "이메일이 이미 존재합니다.", content = @Content(mediaType = ""))
    })
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        UserDTO userDTO = userCreationRequest.getUserDTO();
        String rawPassword = userCreationRequest.getPassword();

        // 이메일 중복 체크
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("이메일이 이미 존재합니다.");
        }

        // 사용자 생성
        UserDTO createdUser = authService.createUser(userDTO, rawPassword);
        String token = authService.login(createdUser.getUserId(), rawPassword).getJwtToken();

        Map<String, Object> response = new HashMap<>();
        response.put("user", createdUser);
        response.put("token", token);

        return ResponseEntity.ok(response); // 생성된 사용자 정보를 반환
    }
}
