package org.study.bootcamp.mishustin.task34.job.analyzer.v1.analytics.model;

import java.time.LocalDate;
import java.util.*;

public record TrendPoint(
        LocalDate periodStart,
        long jobCount,
        List<String> topSkills,
        List<String> topPositions
) { }
