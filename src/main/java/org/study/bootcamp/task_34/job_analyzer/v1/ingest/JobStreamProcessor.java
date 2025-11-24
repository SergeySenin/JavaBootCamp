package org.study.bootcamp.task_34.job_analyzer.v1.ingest;

import org.study.bootcamp.task_34.job_analyzer.v1.domain.model.Job;
import org.study.bootcamp.task_34.job_analyzer.v1.infrastructure.json.JobScraper;

import java.util.*;
import java.util.stream.*;

public final class JobStreamProcessor {

    private final JobScraper jobScraper;

    public JobStreamProcessor(JobScraper jobScraper) {
        this.jobScraper = Objects.requireNonNull(jobScraper, "jobScraper: must not be null");
    }

    public List<Job> processJobs(Stream<String> jsonStream) {
        Objects.requireNonNull(jsonStream, "jsonStream: must not be null");

        return jsonStream
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(jobScraper::parseJob)
                .peek(job -> System.out.println(
                        "Processed job: " + job.position()
                                + " | location=" + job.location()
                                + " | date=" + job.datePosted()))
                .toList();
    }

    public List<Job> processJobsArray(String jsonArray) {
        Objects.requireNonNull(jsonArray, "jsonArray: must not be null");

        List<Job> jobs = jobScraper.parseJobsArray(jsonArray);
        jobs.forEach(job -> System.out.println(
                "Processed job: " + job.position()
                        + " | location=" + job.location()
                        + " | date=" + job.datePosted()));
        return jobs;
    }
}
