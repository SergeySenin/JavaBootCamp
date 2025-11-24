package org.study.bootcamp.task_15.meta_universe.v1.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NotificationType {

    EMAIL("Email"),
    SMS("SMS"),
    PUSH("Push Notification");

    private final String displayName;

    public static NotificationType fromString(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("input: must not be null/blank");
        }
        for (NotificationType type : values()) {
            if (type.displayName.equalsIgnoreCase(input.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown notification type: " + input);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
