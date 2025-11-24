package org.study.bootcamp.task_31.user_activity.v1.domain.model;

import java.time.*;

public record UserAction(
        int userId,
        String userName,
        ActionType actionType,
        LocalDate actionDate,
        String content
) {}
