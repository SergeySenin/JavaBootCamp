package org.study.bootcamp.task_43.people_directory.v1.api.cli;

import org.study.bootcamp.task_43.people_directory.v1.application.service.PeopleInfoProcessing;
import org.study.bootcamp.task_43.people_directory.v1.domain.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo {

    private static final int PEOPLE_COUNT = 10_000;
    private static final int THREADS_COUNT = 4;

    public static void main(String[] args) {

        List<Person> people = new ArrayList<>(PEOPLE_COUNT);

        for (int i = 0; i < PEOPLE_COUNT; i++) {
            String name = "имя" + i;
            String surname = "фамилия" + i;
            int age = 18 + (i % 50);
            String workplace = "компания " + (i % 100);

            people.add(new Person(name, surname, age, workplace));
        }

        PeopleInfoProcessing processing = new PeopleInfoProcessing(
                THREADS_COUNT,
                2,
                TimeUnit.MINUTES
        );

        processing.printPeopleInfo(people);

        System.out.println("программа завершена: все данные выведены");
    }
}
