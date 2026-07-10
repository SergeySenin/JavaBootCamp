package org.study.bootcamp.mishustin.task22.telegram.filters.v1.domain.contract;

@FunctionalInterface
public interface MessageFilter {
    boolean filter(String message);
}
