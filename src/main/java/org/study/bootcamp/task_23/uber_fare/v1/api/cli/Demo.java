package org.study.bootcamp.task_23.uber_fare.v1.api.cli;

import org.study.bootcamp.task_23.uber_fare.v1.application.service.FareCalculator;

import java.util.function.*;

public class Demo {
    public static void main(String[] args) {

        double surgeMultiplier = 1.6;

        FareCalculator fareCalculator = new FareCalculator();

        BiFunction<Double, Double, Double> economyFare =
                (distanceKm, durationMin) -> 1.0 * distanceKm + 0.5 * durationMin;

        BiFunction<Double, Double, Double> comfortFare =
                (distanceKm, durationMin) -> 1.5 * distanceKm + 0.8 * durationMin + 1.99;

        BiFunction<Double, Double, Double> premiumFare =
                (distanceKm, durationMin) -> Math.max(7.5, 2.2 * distanceKm + 1.5 * durationMin);

        BiFunction<Double, Double, Double> economyFareWithSurge =
                (distanceKm, durationMin) -> (1.0 * distanceKm + 0.5 * durationMin) * surgeMultiplier;

        double distanceKm = 10.0;
        double durationMin = 15.0;

        double economyCost = fareCalculator.calculateFare(distanceKm, durationMin, economyFare);
        double comfortCost = fareCalculator.calculateFare(distanceKm, durationMin, comfortFare);
        double premiumCost = fareCalculator.calculateFare(distanceKm, durationMin, premiumFare);
        double economySurgeCost = fareCalculator.calculateFare(distanceKm, durationMin, economyFareWithSurge);

        System.out.println("Economy fare: " + economyCost);
        System.out.println("Comfort fare: " + comfortCost);
        System.out.println("Premium fare: " + premiumCost);
        System.out.println("Economy (surge x" + surgeMultiplier + "): " + economySurgeCost);
    }
}
