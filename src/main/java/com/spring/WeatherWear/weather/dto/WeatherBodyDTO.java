package com.spring.WeatherWear.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherBodyDTO {
    private String dataType;
    private WeatherItemsDTO items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
