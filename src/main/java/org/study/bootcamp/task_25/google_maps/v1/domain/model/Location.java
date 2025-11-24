package org.study.bootcamp.task_25.google_maps.v1.domain.model;

public record Location(String name, double latitude, double longitude) {

    public Location(String name, double latitude, double longitude) {
        validate(name, latitude, longitude);

        this.name = name.trim();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static void validate(String name, double latitude, double longitude) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null/blank");
        }
        if (!Double.isFinite(latitude) || latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("latitude: must be finite and in [-90..90]");
        }
        if (!Double.isFinite(longitude) || longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("longitude: must be finite and in [-180..180]");
        }
    }
}
