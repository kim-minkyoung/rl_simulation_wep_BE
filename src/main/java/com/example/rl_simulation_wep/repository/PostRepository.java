package com.example.rl_simulation_wep.repository;

import com.example.rl_simulation_wep.entity.Board;
import com.example.rl_simulation_wep.entity.Post;
import com.example.rl_simulation_wep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardId(Board boardId, Pageable pageable);
//    List<Post> findByBoardId(Board boardId, int page, int limit, String sort);
//    List<Post> findByUserId(User userId);
}
