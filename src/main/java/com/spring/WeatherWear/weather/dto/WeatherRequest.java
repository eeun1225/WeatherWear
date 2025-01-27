package com.spring.WeatherWear.weather.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WeatherRequest {
    private String serviceKey;
    private String pageNo;
    private String numOfRows;
    private String dataType;
    private String base_date;
    private String base_time;
    private AreaRequest areaRequest;
}
