package org.study.bootcamp.task_9.grade_book_and_journal.v1.infrastructure.console;

import org.study.bootcamp.task_9.grade_book_and_journal.v1.domain.model.Student;
import org.study.bootcamp.task_9.grade_book_and_journal.v1.domain.model.Subject;

import java.util.*;

public final class GradebookPrinter {

    private GradebookPrinter() {}

    public static void printAllStudents(Map<Student, Map<Subject, Integer>> snapshot) {
        if (snapshot.isEmpty()) {
            System.out.println("No students!");
            return;
        }
        snapshot.forEach((student, grades) -> {
            System.out.println("Student: " + student);
            System.out.println("Subjects:");
            if (grades.isEmpty()) {
                System.out.println("- none");
            } else {
                grades.forEach((subject, grade) ->
                        System.out.println("- " + subject + " -> " + grade));
            }
            System.out.println();
        });
    }

    public static void printAllSubjects(Map<Subject, Set<Student>> groupedBySubject) {
        if (groupedBySubject.isEmpty()) {
            System.out.println("No subjects!");
            return;
        }
        groupedBySubject.forEach((subject, students) -> {
            System.out.println("Subject: " + subject);
            System.out.println("Students:");
            if (students.isEmpty()) {
                System.out.println("- none");
            } else {
                students.forEach(s -> System.out.println("- " + s));
            }
            System.out.println();
        });
    }
}
