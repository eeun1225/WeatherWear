package com.spring.WeatherWear.board.controller;

import com.spring.WeatherWear.board.domain.entity.Comment;
import com.spring.WeatherWear.board.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.createComment(postId, comment);
            return ResponseEntity.ok(createdComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("해당 게시글을 찾을 수 없습니다.");
        }
    }

    // 댓글 목록 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        if (comments.isEmpty()) {
            return ResponseEntity.ok("작성된 댓글이 없습니다.");
        }
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody String newContent) {
        try {
            Comment updatedComment = commentService.updateComment(commentId, newContent);
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("해당 댓글을 찾을 수 없습니다.");
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("해당 댓글을 찾을 수 없습니다.");
        }
    }
}
