package com.example.rl_simulation_wep.service;

import com.example.rl_simulation_wep.entity.Gender;
import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
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
