package org.study.bootcamp.task13.bookingsystem.v1.application.observer;

import org.study.bootcamp.task13.bookingsystem.v1.domain.model.Booking;

public interface BookingObserver {
    void update(Booking booking, String status);
}
