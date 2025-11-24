package org.study.bootcamp.task_10.weather_cache.v1.application.service;

import org.study.bootcamp.task_10.weather_cache.v1.infrastructure.provider.WeatherProvider;
import org.study.bootcamp.task_10.weather_cache.v1.domain.model.WeatherData;

import java.time.Clock;
import java.util.*;

public abstract class WeatherCacheTemplate {

    private final WeatherProvider weatherProvider;
    private final Map<String, WeatherData> cacheByCity;
    private final Clock clock;

    public WeatherCacheTemplate(WeatherProvider weatherProvider, Map<String, WeatherData> backingMap, Clock clock) {
        this.weatherProvider = Objects.requireNonNull(weatherProvider, "weatherProvider: must not be null");
        this.cacheByCity = Objects.requireNonNull(backingMap, "backingMap: must not be null");
        this.clock = Objects.requireNonNull(clock, "clock: must not be null");
    }

    public WeatherCacheTemplate(WeatherProvider weatherProvider, Clock clock) {
        this(weatherProvider, new LinkedHashMap<>(), clock);
    }

    public WeatherCacheTemplate(WeatherProvider weatherProvider) {
        this(weatherProvider, new LinkedHashMap<>(), Clock.systemUTC());
    }

    public final WeatherData getWeatherData(String city, long maxCacheAgeMillis) {
        requireNonBlank(city, "city");
        requireNonNegative(maxCacheAgeMillis, "maxCacheAgeMillis");

        String normalizedCityKey = normalizeKey(city);
        WeatherData cachedData = cacheByCity.get(normalizedCityKey);

        if (cachedData != null && !isCacheExpired(cachedData, maxCacheAgeMillis)) {
            return cachedData;
        }

        WeatherData freshData = weatherProvider.fetchWeatherData(sanitizeCityForProvider(city));
        cacheByCity.put(normalizedCityKey, freshData);
        return freshData;
    }

    public final WeatherData forceUpdateWeather(String city) {
        requireNonBlank(city, "city");
        String normalizedCityKey = normalizeKey(city);
        WeatherData freshData = weatherProvider.fetchWeatherData(sanitizeCityForProvider(city));
        cacheByCity.put(normalizedCityKey, freshData);
        return freshData;
    }

    public final int clearExpiredCache(long maxCacheAgeMillis) {
        requireNonNegative(maxCacheAgeMillis, "maxCacheAgeMillis");

        int removedCount = 0;
        Iterator<Map.Entry<String, WeatherData>> entryIterator = cacheByCity.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, WeatherData> mapEntry = entryIterator.next();
            WeatherData weatherData = mapEntry.getValue();
            if (weatherData != null && isCacheExpired(weatherData, maxCacheAgeMillis)) {
                entryIterator.remove();
                removedCount++;
            }
        }
        return removedCount;
    }

    public final void clearAll() {
        cacheByCity.clear();
    }

    public final Map<String, WeatherData> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(cacheByCity));
    }

    protected abstract boolean isCacheExpired(WeatherData weatherData, long maxCacheAgeMillis);

    protected final long nowMillis() {
        return clock.millis();
    }

    private static String normalizeKey(String city) {
        String sanitizedCity = sanitizeCityForProvider(city);
        return sanitizedCity.toLowerCase(Locale.ROOT);
    }

    private static String sanitizeCityForProvider(String city) {
        if (city == null) return null;
        String trimmedCity = city.trim();
        return trimmedCity.replaceAll("\\s+", " ");
    }

    private static void requireNonBlank(String value, String parameterName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(parameterName + ": must not be null/blank");
        }
    }

    private static void requireNonNegative(long value, String parameterName) {
        if (value < 0) {
            throw new IllegalArgumentException(parameterName + ": must be >= 0");
        }
    }
}
