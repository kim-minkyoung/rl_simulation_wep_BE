package com.example.rl_simulation_wep.service;

import com.example.rl_simulation_wep.entity.Gender;
import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAllByOrderByUserScoreDesc();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // userDTO의 각 필드를 체크하고 null이 아닐 경우에만 업데이트
            if (userDTO.getUserName() != null) {
                user.setUserName(userDTO.getUserName());
            }
            if (userDTO.getUserImage() != null) {
                user.setUserImage(userDTO.getUserImage());
            }
            if (userDTO.getUserGender() != null) {
                user.setUserGender(Gender.valueOf(userDTO.getUserGender()));
            }
            if (userDTO.getUserBio() != null) {
                user.setUserBio(userDTO.getUserBio());
            }
            if (userDTO.getUserScore() != null) {
                user.setUserScore(userDTO.getUserScore());
            }

            user.setUserLastLogin(LocalDateTime.now());

            // 수정된 사용자 저장
            userRepository.save(user);
            return convertToDTO(user); // 수정된 사용자 DTO 반환
        }
        return null; // 사용자가 존재하지 않을 경우 null 반환
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setUserName(user.getUserName());
        dto.setUserImage(user.getUserImage());
        dto.setUserGender(user.getUserGender().name());
        dto.setBirthDate(user.getBirthDate());
        dto.setUserBio(user.getUserBio());
        dto.setUserScore(user.getUserScore());
        return dto; // 비밀번호는 포함하지 않음
    }

    User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setUserImage(dto.getUserImage());
        user.setUserGender(Gender.valueOf(dto.getUserGender()));
        user.setBirthDate(dto.getBirthDate());
        user.setUserBio(dto.getUserBio());
        return user;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
