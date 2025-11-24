package org.study.bootcamp.task_22.telegram_filters.v1.domain.contract;

@FunctionalInterface
public interface MessageFilter {
    boolean filter(String message);
}
