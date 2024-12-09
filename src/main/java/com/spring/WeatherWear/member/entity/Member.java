package com.spring.WeatherWear.member.entity;

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

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "password", length = 15)
    private String password;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "nick_name",  length = 20)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type", length = 10)
    private AuthType authType;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
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
