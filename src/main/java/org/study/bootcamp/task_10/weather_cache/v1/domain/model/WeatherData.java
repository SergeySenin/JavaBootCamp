package org.study.bootcamp.task_10.weather_cache.v1.domain.model;

import java.util.regex.Pattern;

public record WeatherData(
        String city,
        double temperature,
        double humidity,
        long timestamp
) {

    private static final Pattern WHITESPACE_RUN = Pattern.compile("\\s+");

    public WeatherData {
        validate(city, temperature, humidity, timestamp);

        city = normalizeCity(city);
    }

    private static void validate(
            String city,
            double temperature,
            double humidity,
            long timestamp
    ) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city: must not be null/blank");
        }
        if (!Double.isFinite(temperature)) {
            throw new IllegalArgumentException("temperature: must be a finite number");
        }
        if (!Double.isFinite(humidity)) {
            throw new IllegalArgumentException("humidity: must be a finite number");
        }
        if (humidity < 0.0 || humidity > 100.0) {
            throw new IllegalArgumentException("humidity: must be in range [0..100]");
        }
        if (timestamp < 0L) {
            throw new IllegalArgumentException("timestamp: must be >= 0");
        }
    }

    private static String normalizeCity(String rawCity) {
        String trimmed = rawCity.trim();
        return WHITESPACE_RUN.matcher(trimmed).replaceAll(" ");
    }
}
