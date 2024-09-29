package com.example.rl_simulation_wep.service;

import com.example.rl_simulation_wep.entity.Gender;
import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::convertToDTO).orElse(null);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setUserName(user.getUserName());
        dto.setPassword(user.getPassword());
        dto.setUserImage(user.getUserImage());
        dto.setUserGender(user.getUserGender().name());
        dto.setBirthDate(user.getBirthDate());
        dto.setUserBio(user.getUserBio());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setUserPassword(dto.getPassword());
        user.setUserImage(dto.getUserImage());
        user.setUserGender(Gender.valueOf(dto.getUserGender()));
        user.setBirthDate(dto.getBirthDate());
        user.setUserBio(dto.getUserBio());
        return user;
    }
}
