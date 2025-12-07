package org.study.bootcamp.task_43.people_directory.v1.application.service;

import org.study.bootcamp.task_43.people_directory.v1.domain.model.Person;

import java.util.List;

public class PersonInfoPrinter implements Runnable {

    private final List<Person> people;

    public PersonInfoPrinter(List<Person> people) {
        if (people == null) {
            throw new IllegalArgumentException("список людей не должен быть null");
        }
        this.people = people;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        for (Person person : people) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            System.out.printf(
                    "[%s] %s %s — %s%n",
                    threadName,
                    person.getName(),
                    person.getSurname(),
                    person.getWorkplace()
            );
        }
    }
}
