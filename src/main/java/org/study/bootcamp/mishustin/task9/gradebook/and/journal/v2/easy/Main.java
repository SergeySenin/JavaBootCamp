package org.study.bootcamp.mishustin.task9.gradebook.and.journal.v2.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static class Student {
        String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        static class Subject {
            String name;

            public Subject(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        static class StudentDatabase {
            private Map<Student, Map<Subject, Integer>> studentSubjects = new HashMap<>();
            private Map<Subject, List<Student>> subjectStudents = new HashMap<>();

            public void addStudentWithSubjects(Student student, Map<Subject, Integer> subjects) {
                studentSubjects.put(student, new HashMap<>(subjects));

                for (Subject subject : subjects.keySet()) {
                    subjectStudents.computeIfAbsent(subject, key -> new ArrayList<>()).add(student);
                }

                System.out.println("Добавлен студент: " + student.name + " с предметами: " + subjects.keySet());
            }

            public void addSubjectForStudent(Student student, Subject subject, int grade) {
                studentSubjects.computeIfAbsent(student, key -> new HashMap<>()).put(subject, grade);

                subjectStudents.computeIfAbsent(subject, key -> new ArrayList<>()).add(student);

                System.out.println("Добавлен предмет " + subject + " для студента " + student + " с оценкой " + grade);
            }

            public void removeStudent(Student student) {
                Map<Subject, Integer> subjects = studentSubjects.get(student);
                if (subjects != null) {
                    for (Subject subject : subjects.keySet()) {
                        List<Student> students = subjectStudents.get(subject);
                        if (students != null) {
                            students.remove(student);
                            if (students.isEmpty()) {
                                subjectStudents.remove(subject);
                            }
                        }
                    }
                }

                studentSubjects.remove(student);
                System.out.println("Удален студент: " + student);
            }

            public void addStudentToSubject(Student student, Subject subject, int grade) {
                addSubjectForStudent(student, subject, grade);
            }

            public void removeStudentFromSubject(Student student, Subject subject) {
                Map<Subject, Integer> subjects = studentSubjects.get(student);
                if (subjects != null) {
                    subjects.remove(subject);
                    if (subjects.isEmpty()) {
                        studentSubjects.remove(student);
                    }
                }

                List<Student> students = subjectStudents.get(subject);
                if (students != null) {
                    students.remove(student);
                    if (students.isEmpty()) {
                        subjectStudents.remove(subject);
                    }
                }

                System.out.println("Удален студент " + student + " из предмета " + subject);
            }

            public void printAllStudents() {
                System.out.println("\n=== ВСЕ СТУДЕНТЫ И ОЦЕНКИ ===");
                for (Map.Entry<Student, Map<Subject, Integer>> studentEntry : studentSubjects.entrySet()) {
                    System.out.println("🎓 " + studentEntry.getKey().name + ":");
                    for (Map.Entry<Subject, Integer> subjectEntry : studentEntry.getValue().entrySet()) {
                        System.out.println("   📚 " + subjectEntry.getKey() + " - " + subjectEntry.getValue());
                    }
                    System.out.println();
                }
            }

            public void printAllSubjects() {
                System.out.println("\n=== ВСЕ ПРЕДМЕТЫ И СТУДЕНТЫ ===");
                for (Map.Entry<Subject, List<Student>> subjectEntry : subjectStudents.entrySet()) {
                    System.out.println("📚 " + subjectEntry.getKey().name + ":");
                    for (Student student : subjectEntry.getValue()) {
                        System.out.println("   👨‍🎓 " + student.name);
                    }
                    System.out.println();
                }
            }
        }

        public static void main(String[] args) {
            StudentDatabase db = new StudentDatabase();

            Student ivan = new Student("Иван");
            Student maria = new Student("Мария");
            Student petr = new Student("Петр");

            Subject math = new Subject("Математика");
            Subject physics = new Subject("Физика");
            Subject history = new Subject("История");

            Map<Subject, Integer> ivanSubjects = new HashMap<>();
            ivanSubjects.put(math, 5);
            ivanSubjects.put(physics, 4);
            db.addStudentWithSubjects(ivan, ivanSubjects);

            Map<Subject, Integer> mariaSubjects = new HashMap<>();
            mariaSubjects.put(math, 5);
            mariaSubjects.put(history, 5);
            db.addStudentWithSubjects(maria, mariaSubjects);

            db.addSubjectForStudent(petr, physics, 3);
            db.addSubjectForStudent(petr, history, 4);

            db.printAllStudents();
            db.printAllSubjects();

            System.out.println("--- Удаляем Петра из физики ---");
            db.removeStudentFromSubject(petr, physics);

            System.out.println("--- Удаляем Марию полностью ---");
            db.removeStudent(maria);

            db.printAllStudents();
            db.printAllSubjects();
        }
    }
}



/*
Зачем нужны две связанные мапы?
Две мапы решают разные задачи и обеспечивают быстрый доступ с разных сторон:

Первая мапа: Map<Student, Map<Subject, Integer>>
Вопрос, на который отвечает: "Какие предметы и оценки у конкретного студента?"
java
// Быстрый поиск всех предметов студента Ивана
Map<Subject, Integer> ivanSubjects = studentSubjects.get(ivan);
// Результат: {Математика=5, Физика=4}

Вторая мапа: Map<Subject, List<Student>>
Вопрос, на который отвечает: "Какие студенты изучают конкретный предмет?"
java
// Быстрый поиск всех студентов, изучающих Математику
List<Student> mathStudents = subjectStudents.get(math);
// Результат: [Иван, Мария]

Преимущества двух мап:
Операция	                С одной мапой	С двумя мапами
Найти предметы студента	    O(1)	O(1)
Найти студентов предмета	O(n)	O(1)
Добавить студента	        O(1)	O(k) + O(1)
Удалить студента	        O(1)	O(k) + O(1)
 */
