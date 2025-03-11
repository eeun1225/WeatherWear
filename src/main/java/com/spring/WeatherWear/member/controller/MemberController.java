package com.spring.WeatherWear.member.controller;

import com.spring.WeatherWear.member.dto.LoginRequest;
import com.spring.WeatherWear.member.dto.SignUpRequest;
import com.spring.WeatherWear.member.entity.Member;
import com.spring.WeatherWear.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
        Member member = memberService.login(loginRequest);

        if (member == null) {
            return "login";
        }

        session.setAttribute("member", member);
        return "success";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/member/signup")
    public String signUpForm() {
        return "signup";
    }

    @PostMapping("/member/signup")
    public String signup(@ModelAttribute SignUpRequest signUpRequest) {
        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodePassword);
        memberService.signUp(signUpRequest);
        return "redirect:/welcome";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
