package com.spring.WeatherWear.board.service;

import com.spring.WeatherWear.board.domain.entity.Comment;
import com.spring.WeatherWear.board.domain.entity.Post;
import com.spring.WeatherWear.board.repository.CommentRepository;
import com.spring.WeatherWear.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 작성
    @Transactional
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    // 특정 게시글의 댓글 목록 조회
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 댓글 수정
    @Transactional
    public Comment updateComment(Long commentId, String newContent) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setContent(newContent);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
