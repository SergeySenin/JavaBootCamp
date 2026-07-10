package org.study.bootcamp.mishustin.task10.weather.cache.v1.infrastructure.provider;

import org.study.bootcamp.mishustin.task10.weather.cache.v1.domain.model.WeatherData;

public interface WeatherProvider {
    WeatherData fetchWeatherData(String city);
}
