package org.study.bootcamp.task_9.grade_book_and_journal.v1.application.service;

import org.study.bootcamp.task_9.grade_book_and_journal.v1.domain.model.Student;
import org.study.bootcamp.task_9.grade_book_and_journal.v1.domain.model.Subject;

import java.util.*;

import static org.study.bootcamp.task_9.grade_book_and_journal.v1.application.shared.Preconditions.requireNonNull;
import static org.study.bootcamp.task_9.grade_book_and_journal.v1.application.shared.Preconditions.requireNonNullList;
import static org.study.bootcamp.task_9.grade_book_and_journal.v1.application.shared.Preconditions.requireNonNullMap;

public class StudentDatabase {

    private final Map<Student, Map<Subject, Integer>> gradesByStudent = new LinkedHashMap<>();

    public void addStudentWithGrades(String studentName, Map<Subject, Integer> subjectsWithGrades) {
        requireNonNullMap(subjectsWithGrades, "subjectsWithGrades");

        Student student = new Student(studentName);
        if (gradesByStudent.containsKey(student)) {
            throw new IllegalArgumentException("student already exists: " + student);
        }

        Map<Subject, Integer> grades = new LinkedHashMap<>();
        subjectsWithGrades.forEach((subject, grade) -> grades.put(norm(subject), grade));
        gradesByStudent.put(student, grades);
    }

    public void addStudentWithGrades(Student student, Map<Subject, Integer> subjectsWithGrades) {
        requireNonNull(student, "student");
        addStudentWithGrades(student.name(), subjectsWithGrades);
    }

    public void addSubjectForStudent(String studentName, Subject subject, Integer grade) {
        boolean added = enroll(studentName, subject, grade);
        if (!added) {
            gradesOf(studentName, false).put(norm(subject), grade);
        }
    }

    public void setGrade(String studentName, Subject subject, Integer newGrade) {
        Map<Subject, Integer> grades = gradesOf(studentName, false);
        Subject key = norm(subject);
        if (!grades.containsKey(key)) {
            throw new IllegalArgumentException("pair (student, subject) not found: " + studentName + ", " + key);
        }
        grades.put(key, newGrade);
    }

    public boolean removeStudent(String studentName) {
        return gradesByStudent.remove(new Student(studentName)) != null;
    }

    public void addSubjectWithStudents(Subject subject, List<String> studentNames) {
        requireNonNull(subject, "subject");
        requireNonNullList(studentNames, "studentNames");
        for (String name : studentNames) {
            if (name == null || name.isBlank()) continue;
            enroll(name, subject, null);
        }
    }

    public boolean addStudentToSubject(Subject subject, String studentName) {
        return enroll(studentName, subject, null);
    }

    public boolean removeStudentFromSubject(Subject subject, String studentName) {
        return unenroll(studentName, subject);
    }

    public Map<Subject, Integer> getGradesOfStudent(String studentName) {
        Map<Subject, Integer> grades = gradesOf(studentName, false);
        return Collections.unmodifiableMap(new LinkedHashMap<>(grades));
    }

    public Set<Student> getStudentsBySubject(Subject subject) {
        Subject key = norm(subject);
        LinkedHashSet<Student> result = new LinkedHashSet<>();
        gradesByStudent.forEach((student, grades) -> {
            if (grades.containsKey(key)) result.add(student);
        });
        return Collections.unmodifiableSet(result);
    }

    public Map<Subject, Set<Student>> groupBySubject() {
        Map<Subject, LinkedHashSet<Student>> grouped = new LinkedHashMap<>();
        gradesByStudent.forEach((student, grades) ->
                grades.keySet().forEach(subject ->
                        grouped.computeIfAbsent(subject, key -> new LinkedHashSet<>()).add(student)));

        Map<Subject, Set<Student>> frozen = new LinkedHashMap<>();
        grouped.forEach((subject, students) ->
                frozen.put(subject, Collections.unmodifiableSet(new LinkedHashSet<>(students))));
        return Collections.unmodifiableMap(frozen);
    }

    public Map<Student, Map<Subject, Integer>> getAllStudentsWithGrades() {
        Map<Student, Map<Subject, Integer>> snapshot = new LinkedHashMap<>();
        gradesByStudent.forEach((student, grades) ->
                snapshot.put(student, Collections.unmodifiableMap(new LinkedHashMap<>(grades))));
        return Collections.unmodifiableMap(snapshot);
    }

    private Map<Subject, Integer> gradesOf(String studentName, boolean needCreate) {
        Student student = new Student(studentName);
        Map<Subject, Integer> map = gradesByStudent.get(student);
        if (map == null) {
            if (!needCreate) {
                throw new IllegalArgumentException("student not found: " + student);
            }
            map = new LinkedHashMap<>();
            gradesByStudent.put(student, map);
        }
        return map;
    }

    private Subject norm(Subject subject) {
        requireNonNull(subject, "subject");
        return new Subject(subject.name());
    }

    private boolean enroll(String studentName, Subject subject, Integer gradeIfNew) {
        Map<Subject, Integer> grades = gradesOf(studentName, true);
        return grades.putIfAbsent(norm(subject), gradeIfNew) == null;
    }

    private boolean unenroll(String studentName, Subject subject) {
        Map<Subject, Integer> grades = gradesOf(studentName, false);
        return grades.remove(norm(subject)) != null;
    }
}
