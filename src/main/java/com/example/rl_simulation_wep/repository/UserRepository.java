package com.example.rl_simulation_wep.repository;

import com.example.rl_simulation_wep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // 이메일 중복 체크 메서드
    List<User> findAllByOrderByUserScoreDesc();
}
