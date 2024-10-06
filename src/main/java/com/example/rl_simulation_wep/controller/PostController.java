package com.example.rl_simulation_wep.controller;

import com.example.rl_simulation_wep.config.JwtTokenUtil;
import com.example.rl_simulation_wep.dto.PostDTO;
import com.example.rl_simulation_wep.dto.SimplePostDTO;
import com.example.rl_simulation_wep.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{boardId}")
    @Operation(summary = "게시판의 게시물 목록 조회 (페이지네이션, 정렬)")
    public ResponseEntity<List<PostDTO>> getPostsByBoardId(
            @PathVariable Long boardId,
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sort) {
        List<PostDTO> posts = postService.getPostsByBoardId(boardId, page, limit, sort);
        return ResponseEntity.ok(posts);
    }

//    @GetMapping("/user/{userId}")
//    @Operation(summary = "사용자의 게시물 목록 조회")
//    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long userId) {
//        List<PostDTO> posts = postService.getPostsByUserId(userId);
//        return ResponseEntity.ok(posts);
//    }
//
//    @GetMapping("/post/{postId}")
//    @Operation(summary = "특정 게시물 조회")
//    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
//        PostDTO post = postService.getPostById(postId);
//        return ResponseEntity.ok(post);
//    }
//
    @PostMapping
    @Operation(summary = "게시물 작성", description = "JWT token 을 header로 전달해야 함(인증 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "403", description = "토큰 이상", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<PostDTO> createPost(@RequestBody SimplePostDTO simplePostDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long authenticatedUserId = Long.valueOf(JwtTokenUtil.extractUserId(token));

        PostDTO createdPost = postService.createPost(authenticatedUserId, simplePostDTO);
        return ResponseEntity.status(201).body(createdPost);
    }
//
//    @PutMapping("/{postId}")
//    @Operation(summary = "게시물 수정", description = "JWT token 을 header로 전달해야 함(인증 필요)")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "게시물 수정 성공", content = @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = PostDTO.class))),
//            @ApiResponse(responseCode = "403", description = "수정할 권한이 없는 사용자", content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음", content = @Content(mediaType = "application/json"))
//    })
//    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO, @RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        Long userId = Long.valueOf(JwtTokenUtil.extractUserId(token));
//
//        PostDTO updatedPost = postService.updatePost(postId, userId, postDTO);
//        return ResponseEntity.ok(updatedPost);
//    }
//
//    @DeleteMapping("/{postId}")
//    @Operation(summary = "게시물 삭제", description = "JWT token 을 header로 전달해야 함(인증 필요)")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "게시물 삭제 성공"),
//            @ApiResponse(responseCode = "403", description = "삭제할 권한이 없는 사용자"),
//            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음")
//    })
//    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        Long userId = Long.valueOf(JwtTokenUtil.extractUserId(token));
//
//        boolean isDeleted = postService.deletePost(postId, userId);
//        if (isDeleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
