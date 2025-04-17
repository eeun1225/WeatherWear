package com.spring.WeatherWear.weather.service;

import com.spring.WeatherWear.weather.dto.*;

import com.spring.WeatherWear.weather.entity.WeatherArea;
import com.spring.WeatherWear.weather.repository.WeatherAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherAreaRepository weatherAreaRepository;

    @Value("${weather.api.key}")
    private String serviceKey;

    @Value("${weather.base.api.url}")
    private String baseUrl;

    @Value("${weather.api.url}")
    private String apiUrl;

    private WeatherData weatherData;

    public WeatherApiResponse getWeather(AreaRequest areaRequest){
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        WeatherRequest weatherRequest = new WeatherRequest(serviceKey, areaRequest);

        WeatherApiResponse weatherApiResponse = webClient.get()
                .uri(apiUrl, weatherRequest.getServiceKey(),
                        weatherRequest.getPageNo(),
                        weatherRequest.getNumOfRows(),
                        weatherRequest.getDataType(),
                        weatherRequest.getBase_date(),
                        weatherRequest.getBase_time(),
                        weatherRequest.getAreaRequest().getNx(),
                        weatherRequest.getAreaRequest().getNy())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block();

        return weatherApiResponse;
    }

    public AreaRequest getLocation(AreaRequest areaRequest) {
        Optional<WeatherArea> nearestArea = weatherAreaRepository.findNearestArea(areaRequest.getLongitude(), areaRequest.getLatitude());

        if (nearestArea.isEmpty()) {
            throw new RuntimeException("가까운 지역을 찾을 수 없습니다.");
        }

        WeatherArea area = nearestArea.get();
        areaRequest.setAreacode(area.getAreaCode());
        areaRequest.setStep1(area.getStep1());
        areaRequest.setStep2(area.getStep2());
        areaRequest.setStep3(area.getStep3());
        areaRequest.setNx(area.getGridX());
        areaRequest.setNy(area.getGridY());

        return areaRequest;
    }

    public WeatherData extractWeatherData(WeatherApiResponse response, AreaRequest areaRequest) {
        if (!"00".equals(response.getResponse().getHeader().getResultCode())) {
            throw new RuntimeException("Weather API 호출 실패: " + response.getResponse().getHeader().getResultMsg());
        }

        List<WeatherItemDTO> items = response.getResponse().getBody().getItems().getItem();
        Map<String, String> weatherData = new HashMap<>();

        for (WeatherItemDTO item : items) {
            weatherData.put(item.getCategory(), String.valueOf(item.getObsrValue()));
        }

        double temperature = Double.parseDouble(weatherData.getOrDefault("T1H", "0"));
        double precipitation = Double.parseDouble(weatherData.getOrDefault("RN1", "0"));
        double windSpeed = Double.parseDouble(weatherData.getOrDefault("WSD", "0"));
        int humidity = (int) Math.round(Double.parseDouble(weatherData.getOrDefault("REH", "0")));
        int pty = (int) Double.parseDouble(weatherData.getOrDefault("PTY", "0"));
        String description = getDescription(pty, humidity, temperature);
        String icon = getIcon(description);
        return new WeatherData(temperature, precipitation, windSpeed, humidity, pty, areaRequest, description, icon);
    }

    public WeatherData getWeatherForLocation(AreaRequest areaRequest) {
        areaRequest = getLocation(areaRequest);
        WeatherApiResponse response = getWeather(areaRequest);
        return extractWeatherData(response, areaRequest);
    }

    private String getDescription(int pty, int humidity, double temperature) {
        if (pty == 1 || pty == 5) return "비";
        if (pty == 3 || pty == 7) return "눈";
        if (pty == 6) return "진눈깨비";
        if (pty == 2) {
            if (temperature <= 0) return "눈";
            if (temperature >= 3) return "비";
            return "진눈깨비";
        }
        if (humidity >= 80) return "흐림";
        if (humidity >= 50) return "구름 많음";
        return "맑음";
    }

    private String getIcon(String description) {
        switch (description) {
            case "비": return "umbrella";
            case "눈": return "ac_unit";
            case "진눈깨비": return "grain";
            case "흐림": return "cloud_queue";
            case "구름 많음": return "cloud";
            default: return "wb_sunny";
        }
    }


}

