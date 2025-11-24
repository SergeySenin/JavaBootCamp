package org.study.bootcamp.task_13.booking_system.v1.application.service;

import org.study.bootcamp.task_13.booking_system.v1.domain.model.Booking;
import org.study.bootcamp.task_13.booking_system.v1.domain.model.Room;
import org.study.bootcamp.task_13.booking_system.v1.application.observer.BookingNotifier;
import org.study.bootcamp.task_13.booking_system.v1.application.observer.BookingObserver;

import java.util.*;

public final class BookingSystem {

    private final Map<Integer, Room> roomsByNumber = new LinkedHashMap<>();
    private final Map<Integer, Booking> bookingsById = new LinkedHashMap<>();
    private final BookingNotifier notifier;
    private int nextBookingId = 1;

    public BookingSystem(BookingNotifier notifier) {
        this.notifier = Objects.requireNonNull(notifier, "notifier: must not be null");
    }

    public BookingSystem() {
        this(new BookingNotifier());
    }

    public void addRoom(Room room) {
        requireNonNull(room, "room");
        int number = room.roomNumber();
        if (roomsByNumber.containsKey(number)) {
            throw new IllegalArgumentException("room already exists: number=" + number);
        }
        roomsByNumber.put(number, room);
    }

    public boolean removeRoom(int roomNumber) {
        Room removed = roomsByNumber.remove(roomNumber);
        if (removed == null) {
            return false;
        }
        List<Integer> toCancel = new ArrayList<>();
        for (Map.Entry<Integer, Booking> entry : bookingsById.entrySet()) {
            if (entry.getValue().room().roomNumber() == roomNumber) {
                toCancel.add(entry.getKey());
            }
        }
        for (Integer bookingId : toCancel) {
            Booking booking = bookingsById.remove(bookingId);
            notifier.notifyObservers(booking, "cancelled");
        }
        return true;
    }

    public List<Room> allRooms() {
        return List.copyOf(roomsByNumber.values());
    }

    public Booking bookRoom(int roomNumber, String date, String timeSlot) {
        requireNonBlank(date, "date");
        requireNonBlank(timeSlot, "timeSlot");

        Room room = roomsByNumber.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("room not found: number=" + roomNumber);
        }
        if (!isRoomAvailable(room, date, timeSlot)) {
            return null;
        }

        int bookingId = nextBookingId++;
        Booking booking = new Booking(bookingId, room, date.trim(), timeSlot.trim());
        bookingsById.put(bookingId, booking);
        notifier.notifyObservers(booking, "created");
        return booking;
    }

    public boolean cancelBooking(int bookingId) {
        Booking removed = bookingsById.remove(bookingId);
        if (removed == null) return false;
        notifier.notifyObservers(removed, "cancelled");
        return true;
    }

    public List<Booking> allBookings() {
        return List.copyOf(bookingsById.values());
    }

    public List<Room> findAvailableRooms(String date, String timeSlot, Set<String> requiredAmenities) {
        requireNonBlank(date, "date");
        requireNonBlank(timeSlot, "timeSlot");
        requireNonNull(requiredAmenities, "requiredAmenities");
        validateAmenities(requiredAmenities);

        String trimmedDate = date.trim();
        String trimmedTimeSlot = timeSlot.trim();

        List<Room> result = new ArrayList<>();
        for (Room room : roomsByNumber.values()) {
            if (room.amenities().containsAll(requiredAmenities)
                    && isRoomAvailable(room, trimmedDate, trimmedTimeSlot)) {
                result.add(room);
            }
        }
        return result;
    }

    public List<Booking> findBookingsForDate(String date) {
        requireNonBlank(date, "date");
        String normalizedDate = date.trim();
        List<Booking> bookingsForDate = new ArrayList<>();
        for (Booking booking : bookingsById.values()) {
            if (booking.date().equals(normalizedDate)) {
                bookingsForDate.add(booking);
            }
        }
        return bookingsForDate;
    }

    public void addObserver(BookingObserver observer) {
        notifier.addObserver(observer);
    }

    public boolean removeObserver(BookingObserver observer) {
        return notifier.removeObserver(observer);
    }

    public int observerCount() {
        return notifier.observerCount();
    }

    private boolean isRoomAvailable(Room room, String date, String timeSlot) {
        for (Booking booking : bookingsById.values()) {
            if (booking.room().roomNumber() == room.roomNumber()
                    && booking.date().equals(date)
                    && booking.timeSlot().equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    private static void validateAmenities(Set<String> amenities) {
        for (String amenity : amenities) {
            if (amenity == null || amenity.isBlank()) {
                throw new IllegalArgumentException("requiredAmenities: must not contain null/blank elements");
            }
        }
    }

    private static void requireNonNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + ": must not be null");
        }
    }

    private static void requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + ": must not be null/blank");
        }
    }
}
