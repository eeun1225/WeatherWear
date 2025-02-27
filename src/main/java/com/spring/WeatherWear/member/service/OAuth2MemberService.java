package com.spring.WeatherWear.member.service;

import com.spring.WeatherWear.member.dto.OAuthAttributes;
import com.spring.WeatherWear.member.entity.Member;
import com.spring.WeatherWear.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, attributes);

        Member member = OAuthSignUp(oAuthAttributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes, "email"
        );
    }

    private Member OAuthSignUp(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail()).orElse(null);
        GenerateNickName generateNickName = new GenerateNickName();

        if (member == null) {
            member = attributes.toEntity(generateNickName.generateName());
            memberRepository.save(member);
        }

        return member;
    }

}
