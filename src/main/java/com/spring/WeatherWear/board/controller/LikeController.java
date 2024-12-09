package com.spring.WeatherWear.board.controller;

import com.spring.WeatherWear.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/add/{boardId}")
    public String addLike(@PathVariable Long boardId, Authentication auth) {
        likeService.addLike(auth.getName(), boardId);
        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/delete/{boardId}")
    public String deleteLike(@PathVariable Long boardId, Authentication auth) {
        likeService.deleteLike(auth.getName(), boardId);
        return "redirect:/boards/" + boardId;
    }
}