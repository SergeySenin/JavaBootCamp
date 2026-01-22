package org.study.bootcamp.task_45.three_little_pigs.v1.api.cli;

import org.study.bootcamp.task_45.three_little_pigs.v1.application.service.Pig1Thread;
import org.study.bootcamp.task_45.three_little_pigs.v1.application.service.Pig2Thread;
import org.study.bootcamp.task_45.three_little_pigs.v1.application.service.Pig3Thread;

public class Demo {

    public static void main(String[] args) {
        Thread pigThreadOne = new Pig1Thread("Ниф-Ниф");
        Thread pigThreadTwo = new Pig2Thread("Нуф-Нуф");
        Thread pigThreadThree = new Pig3Thread("Наф-Наф");

        pigThreadOne.start();
        pigThreadTwo.start();
        pigThreadThree.start();

        try {
            pigThreadOne.join();
            pigThreadTwo.join();
            pigThreadThree.join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            pigThreadOne.interrupt();
            pigThreadTwo.interrupt();
            pigThreadThree.interrupt();
            throw new IllegalStateException("Ожидание завершения игры прервано", exception);
        }

        System.out.println("Игра завершена!");
    }
}
