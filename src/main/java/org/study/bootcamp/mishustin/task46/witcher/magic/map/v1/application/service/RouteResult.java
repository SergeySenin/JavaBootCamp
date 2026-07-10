package org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.application.service;

import org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.domain.model.City;
import org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.domain.model.Monster;

public class RouteResult {

    private final City city;
    private final Monster monster;
    private final double distanceFromCastleToCity;
    private final double distanceFromCityToMonster;
    private final double totalDistance;

    public RouteResult(
            City city,
            Monster monster,
            double distanceFromCastleToCity,
            double distanceFromCityToMonster
    ) {
        if (city == null) {
            throw new IllegalArgumentException("Город не должен быть null");
        }
        if (monster == null) {
            throw new IllegalArgumentException("Монстр не должен быть null");
        }

        this.city = city;
        this.monster = monster;
        this.distanceFromCastleToCity = distanceFromCastleToCity;
        this.distanceFromCityToMonster = distanceFromCityToMonster;
        this.totalDistance = distanceFromCastleToCity + distanceFromCityToMonster;
    }

    public City getCity() {
        return city;
    }

    public Monster getMonster() {
        return monster;
    }

    public double getDistanceFromCastleToCity() {
        return distanceFromCastleToCity;
    }

    public double getDistanceFromCityToMonster() {
        return distanceFromCityToMonster;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
}
