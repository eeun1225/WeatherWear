package com.spring.WeatherWear.Board;

import java.awt.Desktop;
import java.net.URI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherWearApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherWearApplication.class, args);
		openBrowser("http://localhost:8080");
	}

	private static void openBrowser(String url) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception e) {
			System.err.println("Failed to open browser: " + e.getMessage());
		}
	}
}
