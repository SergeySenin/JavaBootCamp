package org.study.bootcamp.task_9.grade_book_and_journal.v1.api.cli;

import org.study.bootcamp.task_9.grade_book_and_journal.v1.domain.model.Subject;
import org.study.bootcamp.task_9.grade_book_and_journal.v1.infrastructure.console.GradebookPrinter;
import org.study.bootcamp.task_9.grade_book_and_journal.v1.application.service.StudentDatabase;

import java.util.*;;

public class Demo {
    public static void main(String[] args) {

        StudentDatabase studentDatabase = new StudentDatabase();

        Subject history = new Subject("History");
        Subject english = new Subject("English");
        Subject math = new Subject("Math");

        studentDatabase.addStudentWithGrades("Alice", Map.of(math, 95, english, 88));
        studentDatabase.addStudentWithGrades("Bob", Map.of(math, 76));

        studentDatabase.addSubjectForStudent("Bob", english, 82);
        studentDatabase.addSubjectWithStudents(history, List.of("Alice", "Charlie", "  ", "Charlie"));
        studentDatabase.addStudentToSubject(english, "Charlie");
        studentDatabase.setGrade("Charlie", english, 70);

        System.out.println("== Students ==");
        GradebookPrinter.printAllStudents(studentDatabase.getAllStudentsWithGrades());

        System.out.println("== Subjects ==");
        GradebookPrinter.printAllSubjects(studentDatabase.groupBySubject());

        studentDatabase.removeStudentFromSubject(english, "Charlie");
        studentDatabase.removeStudent("Bob");

        System.out.println("== After removals ==");
        GradebookPrinter.printAllStudents(studentDatabase.getAllStudentsWithGrades());
        GradebookPrinter.printAllSubjects(studentDatabase.groupBySubject());
    }
}
