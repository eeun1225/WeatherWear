package com.spring.WeatherWear.Board.repository;

import com.spring.WeatherWear.Board.domain.dto.BoardDTO;
import com.spring.WeatherWear.Board.domain.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final SqlSessionTemplate sql;

    public BoardDTO save(BoardDTO boardDTO) {
        sql.insert("Board.save", boardDTO);
        return boardDTO;
    }

    public List<BoardDTO> findAll() {
        System.out.println("findAll");
        return sql.selectList("Board.findAll");
    }

    public List<BoardDTO> findPage(int offset, int pageSize) {
        return sql.selectList("Board.findPage", Map.of("offset", offset, "pageSize", pageSize));
    }

    public int getTotalBoardCount() {
        return sql.selectOne("Board.getTotalBoardCount");
    }

    public List<BoardDTO> findByTitleContaining(String keyword, int page) {
        int offset = (page - 1) * 10;
        // keyword가 null 또는 공백 문자열인 경우 기본 처리
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList(); // 빈 리스트 반환
        }
        return sql.selectList("Board.findByTitleContaining", Map.of("keyword", "%" + keyword.trim() + "%", "offset", offset));
    }

    public List<BoardDTO> findByWriterContaining(String keyword, int page) {
        int offset = (page - 1) * 10;
        // keyword가 null 또는 공백 문자열인 경우 기본 처리
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList(); // 빈 리스트 반환
        }
        return sql.selectList("Board.findByWriterContaining", Map.of("keyword", "%" + keyword.trim() + "%", "offset", offset));
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public void update(BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("Board.saveFile", boardFileDTO);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("Board.findFile", id);
    }
}

