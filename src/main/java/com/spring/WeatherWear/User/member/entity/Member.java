package com.spring.WeatherWear.User.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @NotNull
    @Column(name = "email", length = 50)
    private String email;

    @NotNull
    @Column(name = "password", length = 15)
    private String password;

    @NotNull
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "nickName",  length = 20)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "auth_type", length = 10)
    private AuthType authType;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_id", length = 50)
    private String social_id;


    @Builder
    public Member(String email, String password, String name, String nickName, Role role, AuthType authType, SocialType socialType, String social_id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.role = role;
        this.authType = authType;
        this.socialType = socialType;
        this.social_id = social_id;
    }
}
