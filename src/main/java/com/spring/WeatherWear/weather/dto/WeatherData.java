package com.spring.WeatherWear.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class WeatherData {
    private double temperature; // 기온
    private double precipitation; // 1시간 강수량
    private double windSpeed; // 풍속
    private double humidity; // 습도
    private int pty;
    private AreaRequest areaRequest;
    private String description;
    private String icon;
}
