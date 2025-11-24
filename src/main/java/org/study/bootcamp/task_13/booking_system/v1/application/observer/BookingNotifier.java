package org.study.bootcamp.task_13.booking_system.v1.application.observer;

import lombok.ToString;
import org.study.bootcamp.task_13.booking_system.v1.domain.model.Booking;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@ToString
public final class BookingNotifier {

    private final List<BookingObserver> observers = new CopyOnWriteArrayList<>();

    public void addObserver(BookingObserver observer) {
        requireNonNull(observer, "observer");
        observers.add(observer);
    }

    public boolean removeObserver(BookingObserver observer) {
        requireNonNull(observer, "observer");
        return observers.remove(observer);
    }

    public int observerCount() {
        return observers.size();
    }

    public void notifyObservers(Booking booking, String status) {
        requireNonNull(booking, "booking");
        requireNonBlank(status, "status");
        for (BookingObserver observer : observers) {
            try {
                observer.update(booking, status);
            } catch (RuntimeException ignored) {
            }
        }
    }

    public List<BookingObserver> observersSnapshot() {
        return List.copyOf(observers);
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
