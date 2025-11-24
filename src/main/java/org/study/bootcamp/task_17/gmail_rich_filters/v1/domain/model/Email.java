package org.study.bootcamp.task_17.gmail_rich_filters.v1.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

@Getter
@Setter
@ToString
public final class Email {

    private final String subject;
    private String body;
    private final boolean important;

    public Email(String subject, String body, boolean important) {
        Validate.notBlank(subject, "subject: must not be null/blank");
        Validate.notBlank(body, "body: must not be null/blank");

        this.subject = subject.trim();
        this.body = body.trim();
        this.important = important;
    }
}
