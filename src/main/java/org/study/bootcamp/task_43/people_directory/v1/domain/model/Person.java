package org.study.bootcamp.task_43.people_directory.v1.domain.model;

public class Person {

    private final String name;
    private final String surname;
    private final int age;
    private final String workplace;

    public Person(String name, String surname, int age, String workplace) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("имя не должно быть пустым");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("фамилия не должна быть пустой");
        }
        if (age < 0) {
            throw new IllegalArgumentException("возраст не может быть отрицательным");
        }
        if (workplace == null || workplace.isBlank()) {
            throw new IllegalArgumentException("место работы не должно быть пустым");
        }

        this.name = name;
        this.surname = surname;
        this.age = age;
        this.workplace = workplace;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String getWorkplace() {
        return workplace;
    }
}
