package org.study.bootcamp.task_13.booking_system.v1.domain.model;

import java.util.*;

public record Room(int roomNumber, String type, Set<String> amenities) {

    public Room(int roomNumber, String type, Set<String> amenities) {
        validate(roomNumber, type, amenities);

        this.roomNumber = roomNumber;
        this.type = type.trim();
        this.amenities = Set.copyOf(amenities);
    }

    private static void validate(int roomNumber, String type, Set<String> amenities) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("roomNumber: must be > 0");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type: must not be null/blank");
        }
        if (amenities == null) {
            throw new IllegalArgumentException("amenities: must not be null");
        }
        for (String amenity : amenities) {
            if (amenity == null || amenity.isBlank()) {
                throw new IllegalArgumentException("amenities: must not contain null/blank elements");
            }
        }
    }
}
