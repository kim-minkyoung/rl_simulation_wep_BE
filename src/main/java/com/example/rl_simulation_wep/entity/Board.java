package com.example.rl_simulation_wep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId; // 게시판 id

    @Column(name = "board_name", nullable = false, length = 255) // 게시판 이름
    private String boardName;

    // 기본 생성자
    public Board() {
    }

}
