package org.study.bootcamp.mishustin.task19.error.handling.v1.domain.contract;

@FunctionalInterface
public interface ExceptionHandler<T> {
    T handle(Exception exception);
}
