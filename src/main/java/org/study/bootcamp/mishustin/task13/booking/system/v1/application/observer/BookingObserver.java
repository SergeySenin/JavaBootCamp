package org.study.bootcamp.mishustin.task13.booking.system.v1.application.observer;

import org.study.bootcamp.mishustin.task13.booking.system.v1.domain.model.Booking;

public interface BookingObserver {
    void update(Booking booking, String status);
}
