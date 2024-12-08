package com.spring.WeatherWear.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String nickName;
}
