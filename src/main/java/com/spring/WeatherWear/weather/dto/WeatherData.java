package com.spring.WeatherWear.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherData {
    private double temperature;
    private double windSpeed;
    private double precipitation;
    private int humidity;
    private AreaRequest areaRequest;
}
