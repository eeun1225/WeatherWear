package com.spring.WeatherWear.weather.repository;

import com.spring.WeatherWear.weather.entity.WeatherArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherAreaRepository extends JpaRepository<WeatherArea, String> {
    @Query(value = "SELECT * " +
            "FROM weather_area " +
            "WHERE ABS(CAST(longitude_ms AS DOUBLE) - :longitudeMs) < 1.0 " +
            "  AND ABS(CAST(latitude_ms AS DOUBLE) - :latitudeMs) < 1.0 " +
            "ORDER BY (ABS(CAST(longitude_ms AS DOUBLE) - :longitudeMs) " +
            "        + ABS(CAST(latitude_ms AS DOUBLE) - :latitudeMs)) ASC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<WeatherArea> findNearestArea(@Param("longitudeMs") double longitudeMs,
                                          @Param("latitudeMs") double latitudeMs);
}
