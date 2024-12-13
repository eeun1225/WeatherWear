package com.spring.WeatherWear.service;

import com.spring.WeatherWear.domain.dto.BoardFileDTO;
import com.spring.WeatherWear.repository.BoardRepository;
import com.spring.WeatherWear.domain.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // 업로드된 파일이 있는 경우 파일 크기 확인
        if (boardDTO.getBoardFile() != null && !boardDTO.getBoardFile().isEmpty()) {
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 파일 크기 제한: 10MB
                if (boardFile.getSize() > 10 * 1024 * 1024) { // 10MB를 초과할 경우
                    throw new IllegalArgumentException("업로드 가능한 파일 크기는 최대 10MB입니다.");
                }
            }
            boardDTO.setFileAttached(1);
        } else {
            boardDTO.setFileAttached(0);
        }

        // 게시글 저장
        BoardDTO savedBoard = boardRepository.save(boardDTO);

        // 파일 저장 로직
        if (boardDTO.getFileAttached() == 1) {
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

                // BoardFileDTO 객체 생성 및 설정
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());

                // 파일 저장 경로 설정
                String savePath = "C:/Users/oes05/board_CURD/src/main/resources/upload_files/" + storedFileName;
                boardFile.transferTo(new File(savePath)); // 파일 저장
                boardRepository.saveFile(boardFileDTO); // 파일 정보 저장
            }
        }
    }

    // 기타 메서드는 그대로 유지
    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }
}

