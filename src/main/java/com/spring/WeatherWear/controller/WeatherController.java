package com.spring.WeatherWear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {

    @GetMapping("/")
    public String showWeatherPage(Model model) {
        return "index"; // templates/index.html 반환
    }
}

