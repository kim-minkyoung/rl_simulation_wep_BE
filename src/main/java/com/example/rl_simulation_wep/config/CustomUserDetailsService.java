package com.example.rl_simulation_wep.config;

import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 이메일로 사용자 조회
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + userId);
        }
        // CustomUserDetails로 변환하여 반환 (userId 포함)
        return new CustomUserDetails(user.get().getUserId(), user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }
}
