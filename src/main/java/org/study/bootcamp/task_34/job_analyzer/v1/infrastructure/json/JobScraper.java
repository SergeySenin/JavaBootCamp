package org.study.bootcamp.task_34.job_analyzer.v1.infrastructure.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

import org.study.bootcamp.task_34.job_analyzer.v1.domain.model.Job;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
public final class JobScraper {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    LocalDate.class,
                    (JsonDeserializer<LocalDate>)
                            (json, type, context) -> LocalDate.parse(json.getAsString())
            )
            .create();

    public Job parseJob(String jsonString) {
        Objects.requireNonNull(jsonString, "jsonString must not be null");
        Job parsed = gson.fromJson(jsonString, Job.class);
        if (parsed == null) {
            throw new JsonParseException("Unable to parse Job from JSON!");
        }

        return parsed;
    }

    public List<Job> parseJobsArray(String jsonArrayString) {
        Objects.requireNonNull(jsonArrayString, "jsonArrayString: must not be null");
        Type listType = new TypeToken<List<Job>>() {}.getType();
        List<Job> jobs = gson.fromJson(jsonArrayString, listType);

        return (jobs == null) ? List.of() : jobs;
    }
}
