package org.study.bootcamp.task_34.job_analyzer.v1.application.analytics;

import org.study.bootcamp.task_34.job_analyzer.v1.analytics.model.Granularity;
import org.study.bootcamp.task_34.job_analyzer.v1.domain.model.Job;
import org.study.bootcamp.task_34.job_analyzer.v1.analytics.model.TrendPoint;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class DataAnalyzer {

    private DataAnalyzer() {}

    public static List<String> topSkills(List<Job> jobs, int limitCount) {
        requireNonNull(jobs, "jobs");
        Stream<String> skillsStream = jobs.stream()
                .filter(Objects::nonNull)
                .flatMap(job -> job.requirements().stream())
                .map(string -> string.toLowerCase(Locale.ROOT));
        return frequencyTop(skillsStream, limitCount);
    }

    public static List<String> topPositions(List<Job> jobs, int limitCount) {
        requireNonNull(jobs, "jobs");
        Stream<String> positionsStream = jobs.stream()
                .filter(Objects::nonNull)
                .map(Job::position);
        return frequencyTop(positionsStream, limitCount);
    }

    public static List<String> topLocations(List<Job> jobs, int limitCount) {
        requireNonNull(jobs, "jobs");
        Stream<String> locationsStream = jobs.stream()
                .filter(Objects::nonNull)
                .map(Job::location);
        return frequencyTop(locationsStream, limitCount);
    }

    public static Map<String, Long> salaryDistribution(List<Job> jobs, long bucketSize) {
        requireNonNull(jobs, "jobs");
        if (bucketSize <= 0) throw new IllegalArgumentException("bucketSize: must be > 0");

        return jobs.stream()
                .filter(Objects::nonNull)
                .map(Job::salary)
                .collect(Collectors.groupingBy(
                        salary -> toBucketLabel(salary, bucketSize),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

    public static List<TrendPoint> analyzeTrends(
            List<Job> jobs,
            LocalDate startDate,
            LocalDate endDate,
            Granularity granularity
    ) {
        requireNonNull(jobs, "jobs");
        Objects.requireNonNull(startDate, "startDate: must not be null");
        Objects.requireNonNull(endDate, "endDate: must not be null");
        Objects.requireNonNull(granularity, "granularity: must not be null");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate: must be >= startDate");
        }

        List<Job> jobsInRange = jobs.stream()
                .filter(Objects::nonNull)
                .filter(job -> !job.datePosted().isBefore(startDate) && !job.datePosted().isAfter(endDate))
                .toList();

        Map<LocalDate, List<Job>> jobsByPeriodStart = jobsInRange.stream()
                .collect(Collectors.groupingBy(
                        job -> toPeriodStart(job.datePosted(), granularity),
                        TreeMap::new,
                        Collectors.toList()
                ));

        return jobsByPeriodStart.entrySet().stream()
                .map(entry -> {
                    LocalDate periodStart = entry.getKey();
                    List<Job> periodJobs = entry.getValue();
                    long jobCount = periodJobs.size();
                    List<String> top3Skills = topSkills(periodJobs, 3);
                    List<String> top3Positions = topPositions(periodJobs, 3);
                    return new TrendPoint(periodStart, jobCount, top3Skills, top3Positions);
                })
                .toList();
    }

    private static List<String> frequencyTop(Stream<String> values, int limitCount) {
        Map<String, Long> frequency = values
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(string -> !string.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return frequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(Math.max(0, limitCount))
                .map(Map.Entry::getKey)
                .toList();
    }

    private static String toBucketLabel(long salary, long step) {
        long start = (salary / step) * step;
        long end = start + step;
        return formatK(start) + "–" + formatK(end);
    }

    private static String formatK(long value) {
        return (value / 1_000) + "k";
    }

    private static LocalDate toPeriodStart(LocalDate date, Granularity granularity) {
        return switch (granularity) {
            case DAILY -> date;
            case WEEKLY -> date.with(java.time.DayOfWeek.MONDAY);
            case MONTHLY -> date.with(TemporalAdjusters.firstDayOfMonth());
        };
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(name + " must not be null");
        return value;
    }
}
