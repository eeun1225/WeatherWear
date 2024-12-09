package com.spring.WeatherWear.board.domain.dto;

import com.spring.WeatherWear.board.domain.entity.Board;
import com.spring.WeatherWear.User.member.entity.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardCreateRequest {

    private String title;
    private String body;
    private MultipartFile uploadImage;

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(title)
                .body(body)
                .likeCnt(0)
                .commentCnt(0)
                .build();
    }
}