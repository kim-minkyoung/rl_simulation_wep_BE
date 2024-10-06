package com.example.rl_simulation_wep.service;

import com.example.rl_simulation_wep.dto.PostDTO;
import com.example.rl_simulation_wep.dto.SimplePostDTO;
import com.example.rl_simulation_wep.entity.Board;
import com.example.rl_simulation_wep.entity.Post;
import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.BoardRepository;
import com.example.rl_simulation_wep.repository.PostRepository;
import com.example.rl_simulation_wep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PostDTO> getPostsByBoardId(Long boardId, int page, int limit, String sort) {
        // 1. Sort 설정
        Sort sortOption;
        if ("likes".equals(sort)) {
            sortOption = Sort.by("postLikes").descending().and(Sort.by("postCreatedAt").descending());
        } else {
            sortOption = Sort.by("postCreatedAt").descending();
        }

        // 2. Pageable 생성 (page는 0부터 시작하므로 -1)
        Pageable pageable = (Pageable) PageRequest.of(page - 1, limit, sortOption);

        // 3. Board 엔티티를 가져오기
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        // 4. 해당 board에 속한 게시물 가져오기 (페이징과 정렬 적용)
        List<Post> posts = postRepository.findByBoardId(board, pageable);

        // 5. 엔티티를 DTO로 변환해서 반환
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }



//    public List<PostDTO> getPostsByUserId(Long userId) {
//        List<Post> posts = postRepository.findByUserId(userId);
//        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    public PostDTO getPostById(Long postId) {
//        Optional<Post> post = postRepository.findById(postId);
//        return post.map(this::convertToDTO).orElse(null);
//    }
//
    public PostDTO createPost(Long userId, SimplePostDTO simplePostDTO) {
        Post post = new Post();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        post.setUserId(user);

        Board board = boardRepository.findById(simplePostDTO.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found")); // Board 가져오기
        post.setBoardId(board); // Board 객체 설정

        post.setPostTitle(simplePostDTO.getPostTitle());
        post.setPostContent(simplePostDTO.getPostContent());
        post.setPostCreatedAt(LocalDateTime.now());
        post.setPostLikes(0);
        postRepository.save(post);
        return convertToDTO(post);
    }
//
//    public PostDTO updatePost(Long postId, Long userId, PostDTO postDTO) {
//        Optional<Post> postOptional = postRepository.findById(postId);
//        if (postOptional.isPresent() && postOptional.get().getUserId().equals(userId)) {
//            Post post = postOptional.get();
//            post.setTitle(postDTO.getTitle());
//            post.setContent(postDTO.getContent());
//            postRepository.save(post);
//            return convertToDTO(post);
//        }
//        return null;
//    }
//
//    public boolean deletePost(Long postId, Long userId) {
//        Optional<Post> postOptional = postRepository.findById(postId);
//        if (postOptional.isPresent() && postOptional.get().getUserId().equals(userId)) {
//            postRepository.deleteById(postId);
//            return true;
//        }
//        return false;
//    }

    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setBoardId(post.getBoardId().getBoardId());
        dto.setUserName(post.getUserId().getUserName());
        dto.setPostTitle(post.getPostTitle());
        dto.setPostContent(post.getPostContent());
        return dto;
    }
}
