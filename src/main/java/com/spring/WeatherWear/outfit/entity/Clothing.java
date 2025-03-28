package com.spring.WeatherWear.outfit.entity;

import jakarta.persistence.*;

@Entity
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private WeatherType weatherType;

    private int MaxTemp;
    private int MinTemp;

    private String productLink;
    private String imagePath;
}
