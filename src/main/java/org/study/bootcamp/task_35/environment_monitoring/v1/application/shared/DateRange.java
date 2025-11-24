package org.study.bootcamp.task_35.environment_monitoring.v1.application.shared;

import java.time.LocalDate;
import java.util.*;

public record DateRange(LocalDate startInclusive, LocalDate endInclusive) {

    public DateRange {
        Objects.requireNonNull(startInclusive, "startInclusive");
        Objects.requireNonNull(endInclusive, "endInclusive");
        if (endInclusive.isBefore(startInclusive)) {
            throw new IllegalArgumentException("endInclusive must be >= startInclusive");
        }
    }

    public static DateRange last12MonthsUpTo(LocalDate today) {
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());
        LocalDate start = end.minusMonths(11).withDayOfMonth(1);
        return new DateRange(start, end);
    }
}
