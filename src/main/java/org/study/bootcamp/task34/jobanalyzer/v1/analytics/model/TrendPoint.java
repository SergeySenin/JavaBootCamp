package org.study.bootcamp.task34.jobanalyzer.v1.analytics.model;

import java.time.LocalDate;
import java.util.*;

public record TrendPoint(
        LocalDate periodStart,
        long jobCount,
        List<String> topSkills,
        List<String> topPositions
) { }
