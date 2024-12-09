package com.spring.WeatherWear.board.domain.entity;

import com.spring.WeatherWear.board.domain.BaseEntity;
import com.spring.WeatherWear.User.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;      // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;    // 댓글이 달린 게시판

    public void update(String newBody) {
        this.body = newBody;
    }
}
