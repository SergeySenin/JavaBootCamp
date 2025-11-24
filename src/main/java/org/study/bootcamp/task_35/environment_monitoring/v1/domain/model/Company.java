package org.study.bootcamp.task_35.environment_monitoring.v1.domain.model;

public record Company(long id, String companyName, int totalEmployees) {

    public Company {
        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("companyName: must not be null/blank");
        }
        if (totalEmployees < 0) {
            throw new IllegalArgumentException("totalEmployees: must be >= 0");
        }
        companyName = companyName.trim();
    }
}
