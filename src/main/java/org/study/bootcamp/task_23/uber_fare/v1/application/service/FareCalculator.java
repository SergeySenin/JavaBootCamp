package org.study.bootcamp.task_23.uber_fare.v1.application.service;

import java.util.*;
import java.util.function.*;

public final class FareCalculator {

    public double calculateFare(
            double distanceKm,
            double durationMin,
            BiFunction<Double, Double, Double> pricingRule
    ) {
        requireNonNegative(distanceKm, "distanceKm");
        requireNonNegative(durationMin, "durationMin");
        Objects.requireNonNull(pricingRule, "pricingRule: must not be null");

        double fare = pricingRule.apply(distanceKm, durationMin);
        if (!Double.isFinite(fare)) {
            throw new IllegalArgumentException("pricingRule returned non-finite fare");
        }
        return fare;
    }

    private static void requireNonNegative(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + ": must be finite and >= 0");
        }
    }
}
