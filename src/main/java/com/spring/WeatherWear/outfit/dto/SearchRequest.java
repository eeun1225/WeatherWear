package com.spring.WeatherWear.outfit.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SearchRequest {
    private String query;
    private String display;
    private String start;
    private String sort;

    public SearchRequest(String query){
        this.query = query;
        this.display = "5";
        this.start = "1";
        this.sort = "sim";
    }
}
