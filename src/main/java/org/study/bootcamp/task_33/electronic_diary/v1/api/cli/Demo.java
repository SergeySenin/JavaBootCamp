package org.study.bootcamp.task_33.electronic_diary.v1.api.cli;

import org.study.bootcamp.task_33.electronic_diary.v1.application.service.GradebookService;
import org.study.bootcamp.task_33.electronic_diary.v1.domain.model.Student;
import org.study.bootcamp.task_33.electronic_diary.v1.infrastructure.json.StudentLoader;

import java.io.IOException;
import java.util.*;

public class Demo {
    public static void main(String[] args) {

        List<Student> students;
        try {
            students = StudentLoader.loadFromClasspath("students_test_data.json");
            System.out.println("Loaded students from resources: " + students.size());
        } catch (IOException ioException) {
            System.out.println("No resource found, using fallback demo data.");
            students = fallbackStudents();
        }

        System.out.println("Average by subject: " +
                GradebookService.averageGradePerSubject(students));

        System.out.println("Finals for Ivan Ivanov: " +
                GradebookService.finalGradesForStudent(students, "Ivan", "Ivanov"));

        System.out.println("Most difficult subject: " +
                GradebookService.mostDifficultSubject(students));

        System.out.println("\n--- Performance table ---");
        GradebookService.printPerformanceTable(students);
    }

    private static List<Student> fallbackStudents() {
        Map<String, List<Integer>> aliceCourses = new LinkedHashMap<>();
        aliceCourses.put("Math",       List.of(5, 4, 3, 4));
        aliceCourses.put("Chemistry",  List.of(4, 5, 4));
        aliceCourses.put("Literature", List.of(3, 3, 4));

        Map<String, List<Integer>> bobCourses = new LinkedHashMap<>();
        bobCourses.put("Math",       List.of(3, 4, 3));
        bobCourses.put("Literature", List.of(5, 4, 5));
        bobCourses.put("Physics",    List.of(4, 4));

        Map<String, List<Integer>> caraCourses = new LinkedHashMap<>();
        caraCourses.put("Math",       List.of(5, 5));
        caraCourses.put("Chemistry",  List.of(3, 3, 4));
        caraCourses.put("Physics",    List.of(2, 3));

        return List.of(
                new Student("Ivan",  "Ivanov",  aliceCourses),
                new Student("Petr",  "Petrov",  bobCourses),
                new Student("Daria", "Sidorova", caraCourses)
        );
    }
}
