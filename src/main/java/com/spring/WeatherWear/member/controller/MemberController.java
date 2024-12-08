package com.spring.WeatherWear.member.controller;

import com.spring.WeatherWear.member.dto.SignUpRequest;
import com.spring.WeatherWear.member.entity.Member;
import com.spring.WeatherWear.member.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;

    MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 파일 이름 (templates 폴더에 위치)
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Member member = memberServiceImpl.login(email, password);

        if (member == null) {
            model.addAttribute("errorMessage", "로그인 실패: 이메일 또는 비밀번호를 확인하세요.");
            return "login"; // 로그인 페이지로 다시 이동
        }

        return "redirect:/success"; // 성공 시 success 페이지로 리다이렉트
    }

    @GetMapping("/signup")
    public String SignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute SignUpRequest signUpRequest) {
        memberServiceImpl.signUp(signUpRequest);
        return "redirect:/welcome";
    }


    @GetMapping("/success")
    public String successPage() {
        return "success"; // 뷰 이름 또는 정적 HTML 경로
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome"; // 뷰 이름 또는 정적 HTML 경로
    }


}
