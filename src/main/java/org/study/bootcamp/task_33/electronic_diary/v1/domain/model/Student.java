package org.study.bootcamp.task_33.electronic_diary.v1.domain.model;

import org.apache.commons.lang3.Validate;

import java.util.*;

public record Student(String firstName, String lastName, Map<String, List<Integer>> courses) {

    public Student {
        firstName = trimNotBlank(firstName, "firstName");
        lastName = trimNotBlank(lastName, "lastName");
        Validate.notNull(courses, "courses: must not be null");
        courses = createNormalizedCoursesMap(courses);
    }

    private static Map<String, List<Integer>> createNormalizedCoursesMap(Map<String, List<Integer>> rawCourses) {
        Map<String, List<Integer>> normalized = new LinkedHashMap<>();
        for (Map.Entry<String, List<Integer>> entry : rawCourses.entrySet()) {
            String subject = entry.getKey();
            if (subject == null || subject.isBlank()) continue;

            List<Integer> rawGrades = entry.getValue();
            List<Integer> cleanedGrades = new ArrayList<>();
            if (rawGrades != null) {
                for (Integer grade : rawGrades) {
                    if (grade != null) cleanedGrades.add(grade);
                }
            }
            normalized.put(subject.trim(), List.copyOf(cleanedGrades));
        }
        return Collections.unmodifiableMap(normalized);
    }

    private static String trimNotBlank(String value, String fieldName) {
        Validate.notBlank(value, "%s: must not be null/blank", fieldName);
        return value.trim();
    }
}
