package com.example.rl_simulation_wep.repository;

import com.example.rl_simulation_wep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
