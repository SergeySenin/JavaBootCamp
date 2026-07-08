package org.study.bootcamp.task10.weathercache.v1.application.service;

import org.study.bootcamp.task10.weathercache.v1.infrastructure.provider.WeatherProvider;
import org.study.bootcamp.task10.weathercache.v1.domain.model.WeatherData;

public final class FrequentUpdateWeatherCache extends WeatherCacheTemplate {

    public FrequentUpdateWeatherCache(WeatherProvider weatherProvider) {
        super(weatherProvider);
    }

    @Override
    protected boolean isCacheExpired(WeatherData weatherData, long maxCacheAgeMillis) {
        return true;
    }
}
