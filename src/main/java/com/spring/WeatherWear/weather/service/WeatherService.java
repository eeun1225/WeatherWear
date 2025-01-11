package com.spring.WeatherWear.weather.service;

import com.spring.WeatherWear.weather.dto.*;

import com.spring.WeatherWear.weather.entity.WeatherArea;
import com.spring.WeatherWear.weather.repository.WeatherAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final String serviceKey = "";
    private final WeatherAreaRepository weatherAreaRepository;

    public WeatherApiResponse getWeather(AreaRequest areaRequest){
        WebClient webClient = WebClient.builder()
                .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0").build();

        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.setServiceKey(serviceKey);
        weatherRequest.setPageNo("1");
        weatherRequest.setNumOfRows("1000");
        weatherRequest.setDataType("JSON");
        weatherRequest.setBase_date(currentDate());
        weatherRequest.setBase_time(currentTime());
        weatherRequest.setAreaRequest(areaRequest);

        String apiUrl = "";

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

    public String currentDate(){
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public String currentTime(){
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH00"));
    }


    public AreaRequest getLocation(AreaRequest areaRequest) {
        Optional<WeatherArea> nearestArea = weatherAreaRepository.findNearestArea(areaRequest.getLongitude(), areaRequest.getLatitude());

        if (nearestArea.isPresent()) {
            WeatherArea area = nearestArea.get();

            areaRequest.setAreacode(area.getAreaCode());
            areaRequest.setStep1(area.getStep1());
            areaRequest.setStep2(area.getStep2());
            areaRequest.setStep3(area.getStep3());
            areaRequest.setNx(area.getGridX());
            areaRequest.setNy(area.getGridY());

            return areaRequest;
        } else {
            throw new RuntimeException("가까운 지역을 찾을 수 없습니다.");
        }
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
        double windSpeed = Double.parseDouble(weatherData.getOrDefault("WSD", "0"));
        double precipitation = Double.parseDouble(weatherData.getOrDefault("RN1", "0"));
        double humidityValue = Double.parseDouble(weatherData.getOrDefault("REH", "0"));

        int humidity = (int) Math.round(humidityValue);

        return new WeatherData(temperature, windSpeed, precipitation, humidity, areaRequest);
    }


    public WeatherData getWeatherForLocation(AreaRequest areaRequest) {
        areaRequest = getLocation(areaRequest);
        WeatherApiResponse response = getWeather(areaRequest);
        return extractWeatherData(response, areaRequest);
    }

}

