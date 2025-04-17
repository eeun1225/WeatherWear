package com.spring.WeatherWear.weather.entity;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather_area")
@Getter
public class WeatherArea {
    @Id
    @Column(name = "areacode", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '행정구역코드'")
    private String areaCode;

    @Column(name = "step1", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '시도'")
    private String step1;

    @Column(name = "step2", length = 50, columnDefinition = "VARCHAR(50) COMMENT '시군구'")
    private String step2;

    @Column(name = "step3", length = 50, columnDefinition = "VARCHAR(50) COMMENT '읍면동'")
    private String step3;

    @Column(name = "gridX", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '격자X'")
    private String gridX;

    @Column(name = "gridY", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '격자Y'")
    private String gridY;

    @Column(name = "longitude_hour", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '경도(시)'")
    private String longitudeHour;

    @Column(name = "longitude_min", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '경도(분)'")
    private String longitudeMin;

    @Column(name = "longitude_sec", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '경도(초)'")
    private String longitudeSec;

    @Column(name = "latitude_hour", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '위도(시)'")
    private String latitudeHour;

    @Column(name = "latitude_min", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '위도(분)'")
    private String latitudeMin;

    @Column(name = "latitude_sec", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '위도(초)'")
    private String latitudeSec;

    @Column(name = "longitude_ms", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '경도(초/100)'")
    private String longitudeMs;

    @Column(name = "latitude_ms", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '위도(초/100)'")
    private String latitudeMs;
}
