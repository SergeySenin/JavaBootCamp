package org.study.bootcamp.task_34.job_analyzer.v1.api.cli;

import org.study.bootcamp.task_34.job_analyzer.v1.application.analytics.DataAnalyzer;
import org.study.bootcamp.task_34.job_analyzer.v1.analytics.model.Granularity;
import org.study.bootcamp.task_34.job_analyzer.v1.domain.model.Job;
import org.study.bootcamp.task_34.job_analyzer.v1.infrastructure.json.JobScraper;
import org.study.bootcamp.task_34.job_analyzer.v1.ingest.JobStreamProcessor;
import org.study.bootcamp.task_34.job_analyzer.v1.analytics.model.TrendPoint;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

public class Demo {
    public static void main(String[] args) {

        JobScraper scraper = new JobScraper();
        JobStreamProcessor processor = new JobStreamProcessor(scraper);

        String resourceJson = readResourceAsString("vacancies.json");

        List<Job> jobs = (resourceJson != null)
                ? parseByStructure(resourceJson, processor)
                : sampleJobs();

        System.out.println("\n--- Analysis ---");
        System.out.println("Top skills: " + DataAnalyzer.topSkills(jobs, 5));
        System.out.println("Top positions: " + DataAnalyzer.topPositions(jobs, 5));
        System.out.println("Top locations: " + DataAnalyzer.topLocations(jobs, 5));
        System.out.println("Salary distribution (50k step): " + DataAnalyzer.salaryDistribution(jobs, 50_000));

        System.out.println("\n--- Trends (MONTHLY) ---");
        List<TrendPoint> monthlyTrends =
                DataAnalyzer.analyzeTrends(
                        jobs,
                        LocalDate.now().minusMonths(6),
                        LocalDate.now().plusDays(1),
                        Granularity.MONTHLY
                );

        for (TrendPoint point : monthlyTrends) {
            System.out.println(point.periodStart()
                    + " | count=" + point.jobCount()
                    + " | skills=" + point.topSkills()
                    + " | positions=" + point.topPositions());
        }
    }

    private static List<Job> parseByStructure(String json, JobStreamProcessor processor) {
        String trimmed = json.stripLeading();
        if (trimmed.startsWith("[")) {
            return processor.processJobsArray(json);
        } else {
            try (Stream<String> lines = new BufferedReader(new StringReader(json)).lines()) {
                return processor.processJobs(lines);
            }
        }
    }

    private static String readResourceAsString(String resourceName) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourceName)) {
            if (inputStream == null) return null;
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            return null;
        }
    }

    private static List<Job> sampleJobs() {
        return List.of(
                new Job("Software Engineer", List.of("Java", "Spring", "SQL"),
                        120_000, "New York", LocalDate.now().minusDays(20)),
                new Job("Backend Developer", List.of("Java", "Docker", "Kubernetes"),
                        130_000, "San Francisco", LocalDate.now().minusDays(15)),
                new Job("Data Engineer", List.of("Python", "SQL", "Airflow"),
                        125_000, "New York", LocalDate.now().minusDays(10)),
                new Job("Android Developer", List.of("Kotlin", "Android", "CI/CD"),
                        110_000, "Austin", LocalDate.now().minusDays(5)),
                new Job("DevOps Engineer", List.of("AWS", "Terraform", "Docker"),
                        140_000, "Remote", LocalDate.now().minusDays(2))
        );
    }
}
