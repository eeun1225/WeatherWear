package com.spring.WeatherWear.member.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GenerateNickName {
    private final List<String> baseName = Arrays.asList(new String[]{"맑음이", "촉촉이", "흐림이", "펑펑이"});

    public String generateName(){
        Random random = new Random();
        return baseName.get(random.nextInt(baseName.size())) + random.nextInt(9999);
    }


}
