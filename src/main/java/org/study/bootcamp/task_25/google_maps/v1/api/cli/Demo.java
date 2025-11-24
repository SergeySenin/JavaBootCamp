package org.study.bootcamp.task_25.google_maps.v1.api.cli;

import org.study.bootcamp.task_25.google_maps.v1.application.service.LocationSearchEngine;
import org.study.bootcamp.task_25.google_maps.v1.domain.model.Location;

import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<Location> locations = new ArrayList<>(Arrays.asList(
                new Location("Eiffel Tower",          48.8584,    2.2945),
                new Location("Statue of Liberty",     40.6892,  -74.0445),
                new Location("Great Wall (Badaling)", 40.3652,  116.0204)
        ));

        LocationSearchEngine locationSearchEngine = new LocationSearchEngine();

        List<Location> easternHemisphereLocations = locationSearchEngine.filterLocations(
                locations,
                location -> location.longitude() > 0.0
        );

        System.out.println("=== Eastern hemisphere ===");
        locationSearchEngine.processLocations(
                easternHemisphereLocations,
                location -> System.out.println("Place: " + location.name())
        );

        double baseLatitude = 37.4220;
        double aseLongitude = -122.0841;

        List<Double> distancesFromBase = locationSearchEngine.calculateDistances(
                locations,
                location -> {
                    double latitudeDiff = Math.abs(location.latitude() - baseLatitude);
                    double longitudeDiff = Math.abs(location.longitude() - aseLongitude);
                    return Math.sqrt(latitudeDiff * latitudeDiff + longitudeDiff * longitudeDiff);
                }
        );

        System.out.println("\n=== Distances to base (pseudo) ===");
        for (int i = 0; i < locations.size(); i++) {
            String place = locations.get(i).name();
            double distance = distancesFromBase.get(i);
            System.out.println(place + " : " + String.format(Locale.ROOT, "%.4f", distance));
        }
    }
}
