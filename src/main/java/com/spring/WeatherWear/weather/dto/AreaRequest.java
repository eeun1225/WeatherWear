package com.spring.WeatherWear.weather.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AreaRequest {
    // 행정구역코드
    private String areacode;

    // 시도
    private String step1;

    // 시군구
    private String step2;

    // 읍면동
    private String step3;

    // 예보지점 X좌표
    private String nx;

    // 예보지점 Y좌표
    private String ny;

    // 위도 (현재 위치 기준)
    private double latitude;

    // 경도 (현재 위치 기준)
    private double longitude;
}
