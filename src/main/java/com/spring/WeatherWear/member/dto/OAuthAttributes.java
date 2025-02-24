package com.spring.WeatherWear.member.dto;

import com.spring.WeatherWear.member.entity.AuthType;
import com.spring.WeatherWear.member.entity.Member;
import com.spring.WeatherWear.member.entity.Role;
import com.spring.WeatherWear.member.entity.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final String name;
    private final String email;
    private final String socialId;
    private final SocialType socialType;

    @Builder
    public OAuthAttributes(String name, String email, String socialId, SocialType socialType) {
        this.name = name;
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(attributes);
        }
        throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다: " + registrationId);
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .socialId((String) attributes.get("sub"))
                .socialType(SocialType.GOOGLE)
                .build();
    }

    public Member toEntity(String nickName) {
        return Member.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .role(Role.USER)
                .authType(AuthType.SOCIAL)
                .socialType(socialType)
                .social_id(socialId)
                .build();
    }
}