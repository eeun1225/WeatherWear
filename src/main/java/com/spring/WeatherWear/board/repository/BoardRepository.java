package com.spring.WeatherWear.board.repository;

import com.spring.WeatherWear.board.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 제목으로 검색하며 페이징 처리
    Page<Board> findAllByTitleContains(String title, Pageable pageable);

    // 작성자 닉네임으로 검색하며 페이징 처리
    Page<Board> findAllByMember_NickNameContains(String nickname, Pageable pageable);

    // 특정 사용자가 작성한 모든 게시글 조회
    List<Board> findAllByMember_Email(String loginId);
}


