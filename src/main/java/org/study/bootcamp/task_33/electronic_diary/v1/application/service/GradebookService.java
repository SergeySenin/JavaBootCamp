package org.study.bootcamp.task_33.electronic_diary.v1.application.service;

import org.study.bootcamp.task_33.electronic_diary.v1.domain.model.Student;

import java.util.*;

import static java.util.stream.Collectors.*;

public final class GradebookService {

    private static final double MAX_GRADE = 5.0;

    private GradebookService() {}

    public static Map<String, Double> averageGradePerSubject(List<Student> students) {
        requireNonNull(students, "students");

        return students.stream()
                .filter(Objects::nonNull)
                .flatMap(student -> student.courses().entrySet().stream())
                .collect(groupingBy(
                        Map.Entry::getKey,
                        LinkedHashMap::new,
                        flatMapping(
                                entry -> entry.getValue().stream().map(Integer::doubleValue),
                                averagingDouble(Double::doubleValue)
                        )
                ));
    }

    public static Map<String, Integer> finalGradesForStudent(
            List<Student> students,
            String firstName,
            String lastName
    ) {
        requireNonNull(students, "students");
        String normalizedFirstName = requireNonBlank(firstName, "firstName").trim();
        String normalizedLastName = requireNonBlank(lastName, "lastName").trim();

        return students.stream()
                .filter(Objects::nonNull)
                .filter(student -> student.firstName().equals(normalizedFirstName)
                        && student.lastName().equals(normalizedLastName))
                .findFirst()
                .map(GradebookService::calculateFinalGrades)
                .orElseGet(Collections::emptyMap);
    }

    public static String mostDifficultSubject(List<Student> students) {
        Map<String, Double> averageGradesBySubject = averageGradePerSubject(students);

        return averageGradesBySubject.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");
    }

    public static void printPerformanceTable(List<Student> students) {
        requireNonNull(students, "students");

        List<String> subjects = gatherAllSubjects(students);
        printHeader(subjects);

        for (Student student : students) {
            if (student == null) continue;
            Map<String, Integer> finalGrades = calculateFinalGrades(student);
            printRow(student, subjects, finalGrades);
        }
    }

    private static List<String> gatherAllSubjects(List<Student> students) {
        return students.stream()
                .filter(Objects::nonNull)
                .flatMap(student -> student.courses().keySet().stream())
                .distinct()
                .sorted()
                .toList();
    }

    private static void printHeader(List<String> subjects) {
        System.out.printf("%-20s", "Full name");
        for (String subject : subjects) {
            System.out.printf("| %-12s", subject);
        }
        System.out.printf("| %8s | %12s%n", "%", "Final(5.0)");
    }

    private static void printRow(Student student, List<String> subjects, Map<String, Integer> finalGrades) {
        System.out.printf("%-20s", student.firstName() + " " + student.lastName());

        double sumOfFinals = 0.0;
        for (String subject : subjects) {
            int grade = finalGrades.getOrDefault(subject, 0);
            sumOfFinals += grade;
            System.out.printf("| %12d", grade);
        }

        int subjectCount = Math.max(1, subjects.size());
        double percentage = percentageOfMax(sumOfFinals, subjectCount);
        double finalFivePoint = roundOneDecimal(sumOfFinals / subjectCount);

        System.out.printf("| %8.1f | %12.1f%n", percentage, finalFivePoint);
    }

    private static double percentageOfMax(double total, int subjectCount) {
        return (total / (subjectCount * MAX_GRADE)) * 100.0;
    }

    private static double roundOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private static Map<String, Integer> calculateFinalGrades(Student student) {
        return student.courses().entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> (int) Math.round(entry.getValue().stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0.0)),
                        (existingValue, newValue) -> existingValue,
                        LinkedHashMap::new
                ));
    }

    private static <T> T requireNonNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(name + " must not be null");
        return value;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be null/blank");
        }
        return value;
    }
}
