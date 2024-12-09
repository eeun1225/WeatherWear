package com.spring.WeatherWear.board.controller;

import com.spring.WeatherWear.board.domain.dto.BoardCreateRequest;
import com.spring.WeatherWear.board.domain.dto.BoardDto;
import com.spring.WeatherWear.board.domain.dto.CommentCreateRequest;
import com.spring.WeatherWear.board.domain.dto.BoardSearchRequest;
import com.spring.WeatherWear.board.service.BoardService;
import com.spring.WeatherWear.board.service.CommentService;
import com.spring.WeatherWear.board.service.LikeService;
//import com.spring.WeatherWear.board.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final LikeService likeService;
    private final CommentService commentService;
//    private final S3UploadService s3UploadService;

    @GetMapping
    public String boardListPage(Model model,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false) String sortType,
                                @RequestParam(required = false) String searchType,
                                @RequestParam(required = false) String keyword) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        if (sortType != null) {
            if (sortType.equals("date")) {
                pageRequest = PageRequest.of(page - 1, 10, Sort.by("createdAt").descending());
            } else if (sortType.equals("like")) {
                pageRequest = PageRequest.of(page - 1, 10, Sort.by("likeCnt").descending());
            } else if (sortType.equals("comment")) {
                pageRequest = PageRequest.of(page - 1, 10, Sort.by("commentCnt").descending());
            }
        }

        model.addAttribute("boards", boardService.getBoardList(pageRequest, searchType, keyword));
        model.addAttribute("boardSearchRequest", new BoardSearchRequest(sortType, searchType, keyword));
        return "boards/list";
    }

    @GetMapping("/write")
    public String boardWritePage(Model model) {
        model.addAttribute("boardCreateRequest", new BoardCreateRequest());
        return "boards/write";
    }

    @PostMapping
    public String boardWrite(@ModelAttribute BoardCreateRequest req, Authentication auth, Model model) throws IOException {
        // 작성된 게시글 저장
        Long savedBoardId = boardService.writeBoard(req, auth.getName());

        // 성공 메시지 및 다음 URL 설정
        model.addAttribute("message", savedBoardId + "번 글이 등록되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + savedBoardId);

        return "printMessage";
    }

    @GetMapping("/{boardId}")
    public String boardDetailPage(@PathVariable Long boardId, Model model, Authentication auth) {
        if (auth != null) {
            model.addAttribute("loginUserLoginId", auth.getName());
            model.addAttribute("likeCheck", likeService.checkLike(auth.getName(), boardId));
        }

        BoardDto boardDto = boardService.getBoard(boardId);
        if (boardDto == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다");
            model.addAttribute("nextUrl", "/boards");
            return "printMessage";
        }

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentCreateRequest", new CommentCreateRequest());
        model.addAttribute("commentList", commentService.findAll(boardId));
        return "boards/detail";
    }

    @PostMapping("/{boardId}/edit")
    public String boardEdit(@PathVariable Long boardId, @ModelAttribute BoardDto dto, Model model) throws IOException {
        Long editedBoardId = boardService.editBoard(boardId, dto);

        if (editedBoardId == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/boards");
        } else {
            model.addAttribute("message", editedBoardId + "번 글이 수정되었습니다.");
            model.addAttribute("nextUrl", "/boards/" + boardId);
        }
        return "printMessage";
    }

    @GetMapping("/{boardId}/delete")
    public String boardDelete(@PathVariable Long boardId, Model model) throws IOException {
        Long deletedBoardId = boardService.deleteBoard(boardId);

        model.addAttribute("message", deletedBoardId == null ? "해당 게시글이 존재하지 않습니다" : deletedBoardId + "번 글이 삭제되었습니다.");
        model.addAttribute("nextUrl", "/boards");
        return "printMessage";
    }

//    @ResponseBody
//    @GetMapping("/images/{filename}")
//    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
//        return new UrlResource(s3UploadService.getFullPath(filename));
//    }
//
//    @GetMapping("/images/download/{boardId}")
//    public ResponseEntity<UrlResource> downloadImage(@PathVariable Long boardId) throws MalformedURLException {
//        return s3UploadService.downloadImage(boardId);
//    }
}

