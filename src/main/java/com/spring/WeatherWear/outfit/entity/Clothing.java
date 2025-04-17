package com.spring.WeatherWear.outfit.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    private String productName;
    private int MaxTemp;
    private int MinTemp;
}
