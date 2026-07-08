package org.study.bootcamp.task19.errorhandling.v1.domain.contract;

@FunctionalInterface
public interface ExceptionHandler<T> {
    T handle(Exception exception);
}
