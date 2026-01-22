package org.study.bootcamp.task_46.witcher_magic_map.v1.application.service;

import org.study.bootcamp.task_46.witcher_magic_map.v1.domain.model.City;
import org.study.bootcamp.task_46.witcher_magic_map.v1.domain.model.Location;
import org.study.bootcamp.task_46.witcher_magic_map.v1.domain.model.Monster;

import java.util.List;

public class CityWorker implements Runnable {

    private static final Location CASTLE_LOCATION = new Location(0, 0);

    private final City city;
    private final List<Monster> monsters;
    private final boolean isLoggingEnabled;

    private RouteResult routeResult;

    public CityWorker(City city, List<Monster> monsters, boolean isLoggingEnabled) {
        if (city == null) {
            throw new IllegalArgumentException("Город не должен быть null");
        }
        if (monsters == null) {
            throw new IllegalArgumentException("Список монстров не должен быть null");
        }

        this.city = city;
        this.monsters = monsters;
        this.isLoggingEnabled = isLoggingEnabled;
    }

    @Override
    public void run() {
        if (monsters.isEmpty()) {
            throw new IllegalStateException("Список монстров пуст: невозможно выбрать ближайшего монстра");
        }

        double distanceFromCastleToCity = calculateDistance(CASTLE_LOCATION, city.getLocation());

        Monster closestMonster = null;
        double minimalDistanceFromCityToMonster = Double.POSITIVE_INFINITY;

        for (Monster monster : monsters) {
            double distanceFromCityToMonster = calculateDistance(city.getLocation(), monster.getLocation());
            if (distanceFromCityToMonster < minimalDistanceFromCityToMonster) {
                minimalDistanceFromCityToMonster = distanceFromCityToMonster;
                closestMonster = monster;
            }
        }

        routeResult = new RouteResult(city, closestMonster, distanceFromCastleToCity, minimalDistanceFromCityToMonster);

        if (isLoggingEnabled) {
            String threadName = Thread.currentThread().getName();
            System.out.printf(
                    "[%s] Маршрут: замок → %s (%.2f) + %s → %s (%.2f) = итого %.2f%n",
                    threadName,
                    city.getName(),
                    routeResult.getDistanceFromCastleToCity(),
                    city.getName(),
                    routeResult.getMonster().getName(),
                    routeResult.getDistanceFromCityToMonster(),
                    routeResult.getTotalDistance()
            );
        }
    }

    public RouteResult getRouteResult() {
        return routeResult;
    }

    private double calculateDistance(Location first, Location second) {
        int deltaX = second.getX() - first.getX();
        int deltaY = second.getY() - first.getY();
        return Math.sqrt((double) deltaX * deltaX + (double) deltaY * deltaY);
    }
}
