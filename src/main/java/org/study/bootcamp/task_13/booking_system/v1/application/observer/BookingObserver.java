package org.study.bootcamp.task_13.booking_system.v1.application.observer;

import org.study.bootcamp.task_13.booking_system.v1.domain.model.Booking;

public interface BookingObserver {
    void update(Booking booking, String status);
}
