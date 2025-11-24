package org.study.bootcamp.task_10.weather_cache.v1.application.service;

import org.study.bootcamp.task_10.weather_cache.v1.domain.model.WeatherData;
import org.study.bootcamp.task_10.weather_cache.v1.infrastructure.provider.WeatherProvider;

import java.time.Clock;
import java.util.Objects;
import java.util.Random;

public final class WeatherService implements WeatherProvider {

    private final Clock clock;
    private final Random random;

    public WeatherService(Clock clock, Random random) {
        this.clock = Objects.requireNonNull(clock, "clock: must not be null");
        this.random = Objects.requireNonNull(random, "random: must not be null");
    }

    public WeatherService(Clock clock) {
        this(clock, new Random());
    }

    public WeatherService() {
        this(Clock.systemUTC(), new Random());
    }

    @Override
    public WeatherData fetchWeatherData(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city: must not be null/blank");
        }
        double temperature = nextDoubleInRange(-30.0, 45.0);
        double humidity = nextDoubleInRange(0.0, 100.0);

        long timestamp = clock.millis();
        return new WeatherData(city, temperature, humidity, timestamp);
    }

    private double nextDoubleInRange(double minInclusive, double maxInclusive) {
        if (!Double.isFinite(minInclusive) || !Double.isFinite(maxInclusive) || maxInclusive < minInclusive) {
            throw new IllegalArgumentException("invalid range: [" + minInclusive + ", " + maxInclusive + "]");
        }
        double span = maxInclusive - minInclusive;
        return minInclusive + span * random.nextDouble();
    }
}
