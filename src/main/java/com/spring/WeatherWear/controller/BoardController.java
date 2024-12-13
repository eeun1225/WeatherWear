package com.spring.WeatherWear.controller;

import com.spring.WeatherWear.domain.dto.BoardDTO;
import com.spring.WeatherWear.domain.dto.BoardFileDTO;
import com.spring.WeatherWear.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO, Model model) throws IOException {
        if (boardDTO.getBoardTitle().isEmpty() ||
                boardDTO.getBoardWriter().isEmpty() ||
                boardDTO.getBoardPass().isEmpty() ||
                boardDTO.getBoardContents().isEmpty()) {
            model.addAttribute("error", "모든 필수 입력값을 작성해야 합니다.");
            return "save"; // 다시 작성 화면으로 이동
        }
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/list")    // 글 목록 불러오기
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        System.out.println("boardDTOList = " + boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        // 조회수 처리
        boardService.updateHits(id);
        // 상세내용 가져오기
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        System.out.println("boardDTO = " + boardDTO);
        //파일첨부 추가된 부분
        if(boardDTO.getFileAttached() == 1){
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileDTOList", boardFileDTOList);
        }
        return "detail";
    }

    // 수정버튼 클릭시 수정화면으로 넘어가도록 하는 메서드(GET)
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    // DB에 실질적으로 수정내용을 요청하는 메서드(POST)
    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model) {
        // update요청
        boardService.update(boardDTO);
        // findById로 수정된 내용을 다시조회
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }
}
