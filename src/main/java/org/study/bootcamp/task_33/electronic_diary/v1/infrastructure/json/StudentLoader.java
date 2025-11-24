package org.study.bootcamp.task_33.electronic_diary.v1.infrastructure.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.study.bootcamp.task_33.electronic_diary.v1.domain.model.Student;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public final class StudentLoader {

    private StudentLoader() {}

    public static List<Student> loadFromFilePath(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "filePath: must not be null");
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return parseStudents(reader);
        }
    }

    public static List<Student> loadFromClasspath(String resourceName) throws IOException {
        Objects.requireNonNull(resourceName, "resourceName: must not be null");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found on classpath: " + resourceName);
            }
            try (Reader reader = new InputStreamReader(inputStream)) {
                return parseStudents(reader);
            }
        }
    }

    private static List<Student> parseStudents(Reader reader) throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<JsonStudent>>() {}.getType();
        List<JsonStudent> rawStudents = gson.fromJson(reader, listType);
        if (rawStudents == null) return List.of();

        List<Student> result = new ArrayList<>();
        for (JsonStudent jsonStudent : rawStudents) {
            Map<String, List<Integer>> courses = (jsonStudent.subjects == null) ? Map.of() : jsonStudent.subjects;
            result.add(new Student(jsonStudent.firstName, jsonStudent.lastName, courses));
        }
        return result;
    }

    private static final class JsonStudent {
        String firstName;
        String lastName;
        Map<String, List<Integer>> subjects;
    }
}
