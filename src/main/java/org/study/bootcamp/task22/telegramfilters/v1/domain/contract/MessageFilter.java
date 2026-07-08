package org.study.bootcamp.task22.telegramfilters.v1.domain.contract;

@FunctionalInterface
public interface MessageFilter {
    boolean filter(String message);
}
