package org.study.bootcamp.task10.weathercache.v1.infrastructure.provider;

import org.study.bootcamp.task10.weathercache.v1.domain.model.WeatherData;

public interface WeatherProvider {
    WeatherData fetchWeatherData(String city);
}
