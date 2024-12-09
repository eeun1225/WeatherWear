package com.spring.WeatherWear.User.config;

import com.spring.WeatherWear.User.member.service.OAuth2MemberService;
import com.spring.WeatherWear.User.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, MemberRepository memberRepository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/signup", "/success", "/logout").permitAll() // 로그인 전에는 홈, 로그인 페이지만 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/"))

                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/success")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(new OAuth2MemberService(memberRepository))
                        )
                );

        return http.build();
    }
}
