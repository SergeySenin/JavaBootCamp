package org.study.bootcamp.task_10.weather_cache.v1.api.cli;

import org.study.bootcamp.task_10.weather_cache.v1.domain.model.WeatherData;
import org.study.bootcamp.task_10.weather_cache.v1.infrastructure.provider.WeatherProvider;
import org.study.bootcamp.task_10.weather_cache.v1.application.service.WeatherService;
import org.study.bootcamp.task_10.weather_cache.v1.application.service.FrequentUpdateWeatherCache;
import org.study.bootcamp.task_10.weather_cache.v1.application.service.StandardWeatherCache;
import org.study.bootcamp.task_10.weather_cache.v1.application.service.WeatherCacheTemplate;

import java.time.Clock;

public final class Demo {
    public static void main(String[] args) throws InterruptedException {

        WeatherProvider provider = new WeatherService(Clock.systemUTC());
        WeatherCacheTemplate standardCache = new StandardWeatherCache(provider);
        WeatherCacheTemplate frequentCache = new FrequentUpdateWeatherCache(provider);

        long standardTtlMillis = 1_000;

        WeatherData moscow1 = standardCache.getWeatherData("Moscow", standardTtlMillis);
        WeatherData moscow2 = standardCache.getWeatherData("Moscow", standardTtlMillis);
        System.out.println("standard same instance before ttl? " + (moscow1 == moscow2));

        Thread.sleep(1100);
        WeatherData moscow3 = standardCache.getWeatherData("Moscow", standardTtlMillis);
        System.out.println("standard fetch after ttl? " + (moscow2 != moscow3));

        WeatherData spb1 = frequentCache.getWeatherData("Saint   Petersburg", 60_000);
        WeatherData spb2 = frequentCache.getWeatherData("  SAINT petersburg  ", 60_000);
        System.out.println("frequent always fresh? " + (spb1 != spb2));
    }
}
