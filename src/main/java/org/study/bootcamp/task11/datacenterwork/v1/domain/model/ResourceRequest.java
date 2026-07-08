package org.study.bootcamp.task11.datacenterwork.v1.domain.model;

public record ResourceRequest(double load) {

    public ResourceRequest {
        validateLoad(load);
    }

    private static void validateLoad(double load) {
        if (!Double.isFinite(load) || load <= 0.0) {
            throw new IllegalArgumentException("load: must be finite and > 0");
        }
    }
}
