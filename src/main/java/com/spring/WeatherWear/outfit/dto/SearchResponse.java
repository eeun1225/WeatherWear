package com.spring.WeatherWear.outfit.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<SearchItemDTO> items;

}
