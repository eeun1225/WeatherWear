package com.spring.WeatherWear.weather.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WeatherResponse {
    private WeatherHeaderDTO header;
    private WeatherBodyDTO body;
}