package com.spring.WeatherWear.outfit.service;

import com.spring.WeatherWear.outfit.dto.SearchResponse;
import com.spring.WeatherWear.outfit.entity.Category;
import com.spring.WeatherWear.outfit.entity.Clothing;
import com.spring.WeatherWear.outfit.repository.ClothingRepository;
import com.spring.WeatherWear.weather.dto.WeatherData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class OutfitService {
    private final ClothingRepository clothingRepository;

    public Map<Category, String> findKeywords(WeatherData weather){
        int temp = (int) weather.getTemperature();
        List<Clothing> clothingList = clothingRepository.findByTemperatureRange(temp);
        Map<Category, String> keywords = new HashMap<>();

        for(Clothing c : clothingList){
            keywords.put(c.getCategory(), c.getProductName());
        }

        return keywords;
    }

}
