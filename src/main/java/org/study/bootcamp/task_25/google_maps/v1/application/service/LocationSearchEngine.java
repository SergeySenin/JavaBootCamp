package org.study.bootcamp.task_25.google_maps.v1.application.service;

import org.study.bootcamp.task_25.google_maps.v1.domain.model.Location;

import java.util.*;
import java.util.function.*;

public final class LocationSearchEngine {

    public List<Location> filterLocations(List<Location> locations, Predicate<Location> predicate) {
        requireNonNull(locations, "locations");
        requireNonNull(predicate, "predicate");

        List<Location> result = new ArrayList<>();
        for (Location location : locations) {
            if (location != null && predicate.test(location)) {
                result.add(location);
            }
        }
        return result;
    }

    public void processLocations(List<Location> locations, Consumer<Location> action) {
        requireNonNull(locations, "locations");
        requireNonNull(action, "action");

        for (Location location : locations) {
            if (location != null) {
                action.accept(location);
            }
        }
    }

    public List<Double> calculateDistances(List<Location> locations, Function<Location, Double> distanceFunction) {
        requireNonNull(locations, "locations");
        requireNonNull(distanceFunction, "distanceFunction");

        List<Double> distances = new ArrayList<>();
        for (Location location : locations) {
            if (location == null) continue;
            Double distance = distanceFunction.apply(location);
            if (distance == null) {
                throw new IllegalArgumentException("distanceFunction result: must not be null");
            }
            distances.add(distance);
        }
        return distances;
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + ": must not be null");
        }
        return value;
    }
}
