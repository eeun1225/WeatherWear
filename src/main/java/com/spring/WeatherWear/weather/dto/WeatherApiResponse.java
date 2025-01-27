package com.spring.WeatherWear.weather.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class WeatherApiResponse {
    private WeatherResponse response;
}
