package com.spring.WeatherWear.board.repository;

import com.spring.WeatherWear.board.domain.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByMemberEmailAndBoardId(String loginId, Long boardId);
    Boolean existsByMemberEmailAndBoardId(String loginId, Long boardId);
    List<Like> findAllByMember_Email(String loginId);
}