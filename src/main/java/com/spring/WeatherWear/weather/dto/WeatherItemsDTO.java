package com.spring.WeatherWear.weather.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherItemsDTO {
    private List<WeatherItemDTO> item;
}
