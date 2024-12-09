package com.spring.WeatherWear.board.domain.dto;

import com.spring.WeatherWear.board.domain.entity.Board;
import com.spring.WeatherWear.board.domain.entity.Comment;
import com.spring.WeatherWear.User.member.entity.Member;
import lombok.Data;

@Data
public class CommentCreateRequest {

    private String body;

    //, User user
    public Comment toEntity(Board board, Member member) {
        return Comment.builder()
                .member(member)
                .board(board)
                .body(body)
                .build();
    }
}
