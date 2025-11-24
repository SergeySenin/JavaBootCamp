package org.study.bootcamp.task_22.telegram_filters.v1.application.service;

import org.study.bootcamp.task_22.telegram_filters.v1.domain.contract.MessageFilter;

import java.util.*;

public final class MessageProcessor {

    public boolean processMessage(String message, List<MessageFilter> filters) {
        String normalized = requireNonBlank(message, "message");
        validateFilters(filters);

        for (MessageFilter filter : filters) {
            if (!filter.filter(normalized)) {
                return false;
            }
        }
        return true;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + ": must not be null/blank");
        }
        return value;
    }

    private static void validateFilters(List<MessageFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("filters: must not be null");
        }
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i) == null) {
                throw new IllegalArgumentException("filters[" + i + "]: must not be null");
            }
        }
    }
}
