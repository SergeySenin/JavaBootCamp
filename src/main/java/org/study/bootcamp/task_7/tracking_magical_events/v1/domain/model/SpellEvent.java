package org.study.bootcamp.task_7.tracking_magical_events.v1.domain.model;

public record SpellEvent(int id, String eventType, String action) {

    public SpellEvent {
        validate(id, eventType, action);

        eventType = eventType.trim();
        action = action.trim();
    }

    private static void validate(int id, String eventType, String action) {
        if (id <= 0) {
            throw new IllegalArgumentException("id: must be positive");
        }
        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("eventType: must not be null/blank");
        }
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("action: must not be null/blank");
        }
    }

    @Override
    public String toString() {
        return "SpellEvent{id=%d, type='%s', action='%s'}"
                .formatted(id, eventType, action);
    }
}
