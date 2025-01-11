package com.spring.WeatherWear.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeatherItemDTO {
    private String baseDate;
    private String baseTime;
    private String category;
    private String nx;
    private String ny;
    private Double obsrValue;
}

