package com.spring.WeatherWear.board.service;

import com.spring.WeatherWear.board.domain.entity.Post;
import com.spring.WeatherWear.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // 게시글 목록 조회 (페이징)
    public Page<Post> getPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    // 게시글 상세 조회
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // 게시글 수정
    @Transactional
    public Post updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setUpdatedAt(updatedPost.getUpdatedAt());
            return postRepository.save(post);
        }).orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
