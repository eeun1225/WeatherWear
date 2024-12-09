//package com.spring.WeatherWear.board.repository;
//
//import com.spring.WeatherWear.User.member.entity.Member;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<Member, Long> {
//
//    // 로그인 ID로 사용자 조회
//    Optional<Member> findByLoginId(String loginId);
//
//    // 닉네임 검색 결과 페이징 조회
//    Page<Member> findAllByNicknameContains(String nickname, PageRequest pageRequest);
//
//    // 로그인 ID 중복 여부 확인
//    Boolean existsByLoginId(String loginId);
//
//    // 닉네임 중복 여부 확인
//    Boolean existsByNickname(String nickname);
//}
//
