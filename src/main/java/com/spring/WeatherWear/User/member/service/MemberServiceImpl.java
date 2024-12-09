package com.spring.WeatherWear.User.member.service;

import com.spring.WeatherWear.User.member.dto.SignUpRequest;
import com.spring.WeatherWear.User.member.entity.AuthType;
import com.spring.WeatherWear.User.member.entity.Member;
import com.spring.WeatherWear.User.member.entity.Role;
import com.spring.WeatherWear.User.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void signUp(SignUpRequest signUpRequest) {
        Member member = new Member(
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getName(),
                signUpRequest.getNick_name(),
                Role.USER,
                AuthType.LOCAL,
                null, null
        );
        memberRepository.save(member);
    }

    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }


    public Member login(String email, String password) {
        Optional<Member> memberOpt = memberRepository.findByEmail(email);

        if (memberOpt.isEmpty()) {
            return null;
        }

        Member member = memberOpt.get();

        if (!password.equals(member.getPassword())) {
            return null;
        }

        return member;
    }

}