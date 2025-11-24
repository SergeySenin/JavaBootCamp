package org.study.bootcamp.task_19.error_handling.v1.domain.contract;

@FunctionalInterface
public interface ExceptionHandler<T> {
    T handle(Exception exception);
}
