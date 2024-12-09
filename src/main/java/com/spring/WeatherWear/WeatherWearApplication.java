package com.spring.WeatherWear;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherWearApplication {

	public static void main(String[] args) {
		// .env 파일 로드
		Dotenv dotenv = Dotenv.configure().load();

		// 환경 변수 적용 확인 (Optional, 테스트용)
		System.out.println("AWS_ACCESS_KEY_ID: " + dotenv.get("AWS_ACCESS_KEY_ID"));

		SpringApplication.run(WeatherWearApplication.class, args);
	}
}

