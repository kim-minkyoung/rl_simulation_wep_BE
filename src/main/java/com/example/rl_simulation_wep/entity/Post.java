package com.example.rl_simulation_wep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; // 게시글 id

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 유저 id (FK)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false) // 게시판 id (FK)
    private Board boardId;

    @Column(name = "post_title", nullable = false, length = 255) // 게시글 제목
    private String postTitle;

    @Column(name = "post_content", nullable = false, columnDefinition = "TEXT") // 게시글 내용
    private String postContent;

    @Column(name = "post_likes") // 좋아요 수
    private int postLikes = 0;

    @Column(name = "post_created_at", nullable = false) // 최초 작성 날짜
    private LocalDateTime postCreatedAt = LocalDateTime.now();

    @Column(name = "post_updated_at") // 마지막 수정 날짜
    private LocalDateTime postUpdatedAt = LocalDateTime.now();
}
