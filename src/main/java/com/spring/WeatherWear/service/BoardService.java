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
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            //파일이 없는 경우
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            //파일이 있는 경우
            boardDTO.setFileAttached(1);
            //board를 먼저 insert함
            BoardDTO savedBoard = boardRepository.save(boardDTO);

            //파일처리 후 boardfile insert
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);

                System.out.println(System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                System.out.println("storedFileName = " + storedFileName);

                // BoardFileDTO 객체 생성 및 설정
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());

                String savePath = "C:/Users/oes05/board_CURD/src/main/resources/upload_files/" + storedFileName;
                //실질적으로 파일이 저장되는 코드
                boardFile.transferTo(new File(savePath));
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

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
