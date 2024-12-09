package com.spring.WeatherWear.board.service;

import com.spring.WeatherWear.board.domain.entity.Board;
import com.spring.WeatherWear.board.domain.entity.Like;
import com.spring.WeatherWear.User.member.entity.Member;
import com.spring.WeatherWear.board.repository.BoardRepository;
import com.spring.WeatherWear.board.repository.LikeRepository;
import com.spring.WeatherWear.User.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /**
     * 좋아요 추가
     */
    @Transactional
    public void addLike(String loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Member loginUser = memberRepository.findByEmail(loginId).orElseThrow();

        board.likeChange(board.getLikeCnt() + 1);

        likeRepository.save(Like.builder()
                .member(loginUser)
                .board(board)
                .build());
    }

    /**
     * 좋아요 삭제
     */
    @Transactional
    public void deleteLike(String loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        board.likeChange(board.getLikeCnt() - 1);

        likeRepository.deleteByMemberEmailAndBoardId(loginId, boardId);
    }

    /**
     * 좋아요 확인
     */
    public Boolean checkLike(String loginId, Long boardId) {
        return likeRepository.existsByMemberEmailAndBoardId(loginId, boardId);
    }
}

//@Service
//@RequiredArgsConstructor
//public class LikeService {
//
//    private final LikeRepository likeRepository;
//    private final UserRepository userRepository;
//    private final BoardRepository boardRepository;
//
//    @Transactional
//    public void addLike(String loginId, Long boardId) {
//        Board board = boardRepository.findById(boardId).get();
//        User loginUser = userRepository.findByLoginId(loginId).get();
//        User boardUser = board.getUser();
//
//        // 자신이 누른 좋아요가 아니라면
//        if (!boardUser.equals(loginUser)) {
//            boardUser.likeChange(boardUser.getReceivedLikeCnt() + 1);
//        }
//        board.likeChange(board.getLikeCnt() + 1);
//
//        likeRepository.save(Like.builder()
//                .user(loginUser)
//                .board(board)
//                .build());
//    }
//
//    @Transactional
//    public void deleteLike(String loginId, Long boardId) {
//        Board board = boardRepository.findById(boardId).get();
//        User loginUser = userRepository.findByLoginId(loginId).get();
//        User boardUser = board.getUser();
//
//        // 자신이 누른 좋아요가 아니라면
//        if (!boardUser.equals(loginUser)) {
//            boardUser.likeChange(boardUser.getReceivedLikeCnt() - 1);
//        }
//        board.likeChange(board.getLikeCnt() - 1);
//
//        likeRepository.deleteByUserLoginIdAndBoardId(loginId, boardId);
//    }
//
//    public Boolean checkLike(String loginId, Long boardId) {
//        return likeRepository.existsByUserLoginIdAndBoardId(loginId, boardId);
//    }
//}