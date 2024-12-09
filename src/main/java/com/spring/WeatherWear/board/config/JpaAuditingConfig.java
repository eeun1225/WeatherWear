package com.spring.WeatherWear.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
// JPA의 Auditing 기능을 활성화, 엔티티(Entity)에서 자동으로 생성일자, 수정일자 등을 관리할 수 있게 해줌.
public class JpaAuditingConfig {
}
