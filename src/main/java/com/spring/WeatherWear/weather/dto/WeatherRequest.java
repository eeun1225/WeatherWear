package com.spring.WeatherWear.weather.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public WeatherRequest(String serviceKey, AreaRequest areaRequest) {
        this.serviceKey = serviceKey;
        this.pageNo = "1";
        this.numOfRows = "1000";
        this.dataType = "JSON";
        this.base_date = currentDate();
        this.base_time = currentTime();
        this.areaRequest = areaRequest;
    }

    private String currentDate(){
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    private String currentTime(){
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH00"));
    }

}
