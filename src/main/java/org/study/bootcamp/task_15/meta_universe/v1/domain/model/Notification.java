package org.study.bootcamp.task_15.meta_universe.v1.domain.model;

import org.apache.commons.lang3.Validate;

public record Notification(
        NotificationType type,
        String message
) {
    public Notification {
        Validate.notNull(type, "type must not be null");
        Validate.notBlank(message, "message must not be null/blank");

        message = message.trim();
    }

    public Notification withMessage(String newMessage) {
        return new Notification(this.type, newMessage);
    }
}
