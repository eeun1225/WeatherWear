package com.spring.WeatherWear.board.service;

import com.spring.WeatherWear.board.domain.dto.BoardCntDto;
import com.spring.WeatherWear.board.domain.dto.BoardCreateRequest;
import com.spring.WeatherWear.board.domain.dto.BoardDto;
import com.spring.WeatherWear.board.domain.entity.*;
import com.spring.WeatherWear.board.repository.BoardRepository;
import com.spring.WeatherWear.board.repository.CommentRepository;
import com.spring.WeatherWear.board.repository.LikeRepository;
import com.spring.WeatherWear.User.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final S3UploadService s3UploadService;

    /**
     * 게시글 목록 조회 (검색 및 페이징 처리)
     */
    public Page<Board> getBoardList(PageRequest pageRequest, String searchType, String keyword) {
        if (pageRequest == null) {
            throw new IllegalArgumentException("PageRequest cannot be null");
        }

        if (keyword == null || keyword.isEmpty() || searchType == null || searchType.isEmpty()) {
            // 검색 조건이 없는 경우 전체 게시글 반환
            return boardRepository.findAll(pageRequest);
        }

        if (Objects.equals(searchType, "title")) {
            return boardRepository.findAllByTitleContains(keyword, pageRequest);
        } else if (Objects.equals(searchType, "nickname")) {
            return boardRepository.findAllByMember_NickNameContains(keyword, pageRequest);
        }

        // 기본 동작으로 전체 게시글 반환
        return boardRepository.findAll(pageRequest);
    }

    /**
     * 게시글 상세 조회
     */
    public BoardDto getBoard(Long boardId) {
        Optional<Board> optBoard = boardRepository.findById(boardId);
        return optBoard.map(BoardDto::of).orElse(null);
    }

    /**
     * 게시글 작성
     */
    @Transactional
    public Long writeBoard(BoardCreateRequest req, String loginId) throws IOException {
        // 작성자 정보 가져오기
        var loginUser = memberRepository.findByEmail(loginId).orElseThrow();

        // 게시글 저장
        var savedBoard = boardRepository.save(req.toEntity(loginUser));

        // 이미지 업로드 처리
        var uploadImage = s3UploadService.saveImage(req.getUploadImage(), savedBoard);
        if (uploadImage != null) {
            savedBoard.setUploadImage(uploadImage);
        }
        return savedBoard.getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long editBoard(Long boardId, BoardDto dto) throws IOException {
        var board = boardRepository.findById(boardId).orElseThrow();

//        // 기존 이미지 삭제
        if (board.getUploadImage() != null) {
            s3UploadService.deleteImage(board.getUploadImage());
            board.setUploadImage(null);
        }

        // 새 이미지 업로드 처리
        var uploadImage = s3UploadService.saveImage(dto.getNewImage(), board);
        if (uploadImage != null) {
            board.setUploadImage(uploadImage);
        }

        // 게시글 업데이트
        board.update(dto);
        return board.getId();
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long deleteBoard(Long boardId) {
        var board = boardRepository.findById(boardId).orElseThrow();

//        // 기존 이미지 삭제
        if (board.getUploadImage() != null) {
            s3UploadService.deleteImage(board.getUploadImage());
        }

        // 게시글 삭제
        boardRepository.deleteById(boardId);
        return boardId;
    }

    /**
     * 내가 작성한 게시글 / 좋아요 누른 게시글 / 댓글 작성한 게시글 조회
     */
    public List<Board> findMyBoard(String type, String loginId) {
        if (type.equals("board")) {
            return boardRepository.findAllByMember_Email(loginId);
        } else if (type.equals("like")) {
            var likes = likeRepository.findAllByMember_Email(loginId);
            List<Board> boards = new ArrayList<>();
            for (Like like : likes) {
                boards.add(like.getBoard());
            }
            return boards;
        } else if (type.equals("comment")) {
            var comments = commentRepository.findAllByMember_Email(loginId);
            List<Board> boards = new ArrayList<>();
            HashSet<Long> commentIds = new HashSet<>();

            for (Comment comment : comments) {
                if (!commentIds.contains(comment.getBoard().getId())) {
                    boards.add(comment.getBoard());
                    commentIds.add(comment.getBoard().getId());
                }
            }
            return boards;
        }
        return null;
    }

    /**
     * 게시판 통계 조회
     */
    public BoardCntDto getBoardCnt() {
        return BoardCntDto.builder()
                .totalBoardCnt(boardRepository.count())
                .build();
    }
}
