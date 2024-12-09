package com.spring.WeatherWear.service;

import com.spring.WeatherWear.domain.Content;
import com.spring.WeatherWear.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    //    글작성
    public void writeContent(Content content) {
        contentRepository.save(content);
    }

    //    글수정
    public void editContent(int id, String texts, String password) {
        Content content = contentRepository.findById(id);
        if (!content.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            return;
        }
        content.setTexts(texts);

        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        content.setUpdateDate(formattedDate);

        contentRepository.edit(id, content);
    }

    //    글삭제
    public void deleteContent(int id, String password) {
        Content content = contentRepository.findById(id);
        if (!content.getPassword().equals(password)) {
            return;
        }
        contentRepository.delete(id);
    }

    //    전체 글 조회
    public List<Content> getAllContents() {
        List<Content> allContents = contentRepository.findAll();
        System.out.println("모든 글: " + allContents);
        return allContents;
    }


    //    id로 글 조회
    public Content getContent(int id) {
        return contentRepository.findById(id);
    }


}

