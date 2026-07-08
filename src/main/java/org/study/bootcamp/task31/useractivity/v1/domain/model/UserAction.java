package org.study.bootcamp.task31.useractivity.v1.domain.model;

import java.time.*;

public record UserAction(
        int userId,
        String userName,
        ActionType actionType,
        LocalDate actionDate,
        String content
) {}
