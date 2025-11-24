package org.study.bootcamp.task_17.gmail_rich_filters.v1.application.service;

import org.study.bootcamp.task_17.gmail_rich_filters.v1.domain.model.Email;

import java.util.*;
import java.util.function.*;

public final class EmailProcessor {

    public void processEmails(
            List<Email> emails,
            Predicate<Email> filter,
            Function<Email, String> transformer,
            Consumer<Email> action
    ) {
        Objects.requireNonNull(emails, "emails: must not be null");
        Objects.requireNonNull(filter, "filter: must not be null");
        Objects.requireNonNull(transformer, "transformer: must not be null");
        Objects.requireNonNull(action, "action: must not be null");

        for (Email email : emails) {
            if (filter.test(email)) {
                String transformedBody = transformer.apply(email);
                email.setBody(transformedBody);
                action.accept(email);
            }
        }
    }
}
