package com.spring.WeatherWear.weather.controller;

import com.spring.WeatherWear.weather.dto.*;
import com.spring.WeatherWear.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather() {
        return "weather";
    }

    @PostMapping("/weather")
    public ResponseEntity<WeatherData> api_weather(@RequestBody Map<String, Double> coordinates) {
        AreaRequest areaRequest = new AreaRequest();
        areaRequest.setLatitude(coordinates.get("latitude"));
        areaRequest.setLongitude(coordinates.get("longitude"));

        WeatherData weatherData = weatherService.getWeatherForLocation(areaRequest);
        return ResponseEntity.ok(weatherData);
    }
}
