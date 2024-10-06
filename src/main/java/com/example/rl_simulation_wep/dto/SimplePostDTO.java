package com.example.rl_simulation_wep.dto;

import com.example.rl_simulation_wep.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimplePostDTO {
    private Long boardId;
    private String postTitle;
    private String postContent;
    private int postLikes;
}
