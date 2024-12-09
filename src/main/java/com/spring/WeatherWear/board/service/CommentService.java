package com.spring.WeatherWear.board.service;

import com.spring.WeatherWear.board.domain.dto.CommentCreateRequest;
import com.spring.WeatherWear.board.domain.entity.Board;
import com.spring.WeatherWear.board.domain.entity.Comment;
import com.spring.WeatherWear.User.member.entity.Member;
import com.spring.WeatherWear.board.repository.BoardRepository;
import com.spring.WeatherWear.board.repository.CommentRepository;
import com.spring.WeatherWear.User.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 작성
     */
    public void writeComment(Long boardId, CommentCreateRequest req, String loginId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Member member = memberRepository.findByEmail(loginId).orElseThrow();
        board.commentChange(board.getCommentCnt() + 1);
        commentRepository.save(req.toEntity(board, member));
    }

    /**
     * 댓글 조회
     */
    public List<Comment> findAll(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public Long editComment(Long commentId, String newBody, String loginId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Member member = memberRepository.findByEmail(loginId).orElseThrow();

        // 댓글 작성자만 수정 가능
        if (!comment.getMember().equals(member)) {
            return null;
        }

        comment.update(newBody);
        return comment.getBoard().getId();
    }

    /**
     * 댓글 삭제
     */
    public Long deleteComment(Long commentId, String loginId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Member member = memberRepository.findByEmail(loginId).orElseThrow();

        // 작성자만 삭제 가능
        if (!comment.getMember().equals(member)) {
            return null;
        }

        Board board = comment.getBoard();
        board.commentChange(board.getCommentCnt() - 1);
        commentRepository.delete(comment);

        return board.getId();
    }
}