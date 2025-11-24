package org.study.bootcamp.task_15.meta_universe.v1.application.service;

import org.study.bootcamp.task_15.meta_universe.v1.domain.model.Notification;
import org.study.bootcamp.task_15.meta_universe.v1.domain.model.NotificationType;

import java.util.*;
import java.util.function.*;

public final class NotificationManager {

    private final Map<NotificationType, Consumer<Notification>> handlers = new EnumMap<>(NotificationType.class);
    private final List<Predicate<Notification>> filters = new ArrayList<>();
    private final List<Function<Notification, Notification>> transformers = new ArrayList<>();

    private Consumer<Notification> missingHandler = notification ->
            System.err.println("No handler for type: " + notification.type() + " -> " + notification);

    public void registerHandler(NotificationType type, Consumer<Notification> handler) {
        requireNonNull(type, "type");
        requireNonNull(handler, "handler");
        handlers.put(type, handler);
    }

    public void sendNotification(Notification notification) {
        requireNonNull(notification, "notification");

        for (Predicate<Notification> filter : filters) {
            if (!filter.test(notification)) {
                return;
            }
        }

        Notification transformed = notification;
        for (Function<Notification, Notification> transformer : transformers) {
            transformed = requireNonNull(transformer.apply(transformed), "transformer result");
        }

        Consumer<Notification> handler = handlers.get(transformed.type());
        if (handler != null) {
            handler.accept(transformed);
        } else {
            missingHandler.accept(transformed);
        }
    }

    public void addFilter(Predicate<Notification> filter) {
        requireNonNull(filter, "filter");
        filters.add(filter);
    }

    public void addTransformer(Function<Notification, Notification> transformer) {
        requireNonNull(transformer, "transformer");
        transformers.add(transformer);
    }

    public void setMissingHandler(Consumer<Notification> fallbackHandler) {
        requireNonNull(fallbackHandler, "fallbackHandler");
        this.missingHandler = fallbackHandler;
    }

    private static <T> T requireNonNull(T value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException(paramName + ": must not be null");
        }
        return value;
    }
}
