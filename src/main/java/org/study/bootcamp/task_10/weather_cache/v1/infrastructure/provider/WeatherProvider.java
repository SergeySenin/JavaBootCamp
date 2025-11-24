package org.study.bootcamp.task_10.weather_cache.v1.infrastructure.provider;

import org.study.bootcamp.task_10.weather_cache.v1.domain.model.WeatherData;

public interface WeatherProvider {
    WeatherData fetchWeatherData(String city);
}
