package com.spring.WeatherWear.User.member.service;

import com.spring.WeatherWear.User.member.dto.LoginRequest;
import com.spring.WeatherWear.User.member.dto.SignUpRequest;
import com.spring.WeatherWear.User.member.entity.Member;
import com.spring.WeatherWear.User.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public void signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toEntity();
        memberRepository.save(member);
    }

    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }


    public Member login(LoginRequest loginRequest) {
        Optional<Member> byMemberEmail = memberRepository.findByEmail(loginRequest.getEmail());

        if (byMemberEmail.isPresent()) {
            Member member = byMemberEmail.get();
            if (member.getPassword().equals(loginRequest.getPassword())) {
                return member;
            }
        }

        return null;
    }

}