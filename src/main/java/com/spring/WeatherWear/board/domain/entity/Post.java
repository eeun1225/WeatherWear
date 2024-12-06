package com.spring.WeatherWear.board.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false)
    private int likes = 0;

    @Column(nullable = false)
    private boolean isPinned = false;

    @Column
    private String images; // 이미지 경로를 콤마로 구분해 저장

    // 기본 생성자
    protected Post() {}

    // 생성자
    public Post(String title, String content, String author, boolean isPinned, String images) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.isPinned = isPinned;
        this.images = images;
    }

    // getter/setter 생략 (필요시 추가)
}
