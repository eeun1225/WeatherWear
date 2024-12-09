package com.spring.WeatherWear.User.member.dto;

import com.spring.WeatherWear.User.member.entity.AuthType;
import com.spring.WeatherWear.User.member.entity.Member;
import com.spring.WeatherWear.User.member.entity.Role;
import lombok.*;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String nick_name;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickName(nick_name)
                .role(Role.USER)
                .authType(AuthType.LOCAL)
                .socialType(null)
                .social_id(null)
                .build();
    }
}