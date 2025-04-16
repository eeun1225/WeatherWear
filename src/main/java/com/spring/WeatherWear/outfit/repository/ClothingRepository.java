package com.spring.WeatherWear.outfit.repository;

import com.spring.WeatherWear.outfit.entity.Clothing;
import com.spring.WeatherWear.outfit.entity.WeatherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    @Query("SELECT c FROM Clothing c WHERE :temp BETWEEN c.MinTemp AND c.MaxTemp")
    List<Clothing> findByTemperatureRange(@Param("temp") int temp);
    Optional<Clothing> findByWeatherType(WeatherType weatherType);
}
