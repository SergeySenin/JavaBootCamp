package org.study.bootcamp.mishustin.task31.user.activity.v1.domain.model;

import java.time.*;

public record UserAction(
        int userId,
        String userName,
        ActionType actionType,
        LocalDate actionDate,
        String content
) {}
