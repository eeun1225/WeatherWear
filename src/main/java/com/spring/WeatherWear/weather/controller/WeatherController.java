package com.spring.WeatherWear.weather.controller;

import com.spring.WeatherWear.weather.dto.*;
import com.spring.WeatherWear.weather.service.WeatherService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<WeatherData> api_weather(@RequestBody Map<String, Double> coordinates, HttpSession session) {
        AreaRequest areaRequest = new AreaRequest();
        areaRequest.setLatitude(coordinates.get("latitude"));
        areaRequest.setLongitude(coordinates.get("longitude"));

        WeatherData weatherData = weatherService.getWeatherForLocation(areaRequest);
        session.setAttribute("weather", weatherData);
        return ResponseEntity.ok(weatherData);
    }

}
