package com.example.rl_simulation_wep.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDTO {
    private Long postId;
    private Long userId;
    private Long boardId;
    private String postTitle;
    private String postContent;
    private int postLikes;
}
