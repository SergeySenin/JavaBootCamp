package org.study.bootcamp.task_38.army_of_heroes.v1.application.service;

import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Squad;

public class SquadPowerReporter extends Thread {

    private final Squad squad;
    private int result;

    public SquadPowerReporter(Squad squad) {
        this.squad = squad;
    }

    @Override
    public void run() {
        try {
            result = squad.calculateSquadPower();

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Расчёт силы отряда '" + squad.getName() + "' был отменён!");
                return;
            }

            System.out.println("Отряд '" + squad.getName() + "' - рассчитанная сила: " + result);
        } catch (Exception exception) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Расчёт силы отряда '" + squad.getName() + "' прерван из-за исключения!");
            }
        }
    }

    public int getResult() {
        return result;
    }
}
