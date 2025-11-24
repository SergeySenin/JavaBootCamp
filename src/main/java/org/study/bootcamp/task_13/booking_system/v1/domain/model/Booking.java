package org.study.bootcamp.task_13.booking_system.v1.domain.model;

import java.util.*;

public record Booking(int bookingId, Room room, String date, String timeSlot) {

    public Booking(int bookingId, Room room, String date, String timeSlot) {
        validate(bookingId, room, date, timeSlot);

        this.bookingId = bookingId;
        this.room = room;
        this.date = date.trim();
        this.timeSlot = timeSlot.trim();
    }

    private static void validate(int bookingId, Room room, String date, String timeSlot) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("bookingId: must be > 0");
        }
        Objects.requireNonNull(room, "room: must not be null");
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("date: must not be null/blank");
        }
        if (timeSlot == null || timeSlot.isBlank()) {
            throw new IllegalArgumentException("timeSlot: must not be null/blank");
        }
    }
}
