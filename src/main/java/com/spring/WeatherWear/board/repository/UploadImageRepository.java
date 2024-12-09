package com.spring.WeatherWear.board.repository;

import com.spring.WeatherWear.board.domain.entity.UploadImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadImageRepository extends JpaRepository<UploadImage, Long> {
}
