package org.study.bootcamp.mishustin.task35.environment.monitoring.v1.domain.model;

import java.time.LocalDate;

public record EnvironmentalImpact(
        long id,
        long companyId,
        double volume,
        LocalDate date,
        EnvironmentalImpactType type
) {
    public EnvironmentalImpact {
        if (!Double.isFinite(volume) || volume < 0.0) {
            throw new IllegalArgumentException("volume: must be finite and >= 0");
        }
        if (date == null) {
            throw new IllegalArgumentException("date: must not be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type: must not be null");
        }
    }
}
