package org.study.bootcamp.task_38.army_of_heroes.v1.application.service;

import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Squad;

public class SquadPowerCalculator extends Thread {

    private final Squad squad;
    private int calculatedPower;
    private Throwable calculationError;

    public SquadPowerCalculator(Squad squad) {
        this.squad = squad;
    }

    @Override
    public void run() {
        try {
            calculatedPower = squad.calculateSquadPower();
            System.out.println("отряд '" + squad.getName() + "' - рассчитанная сила: " + calculatedPower);
        } catch (Throwable throwable) {
            calculationError = throwable;
        }
    }

    public int getCalculatedPower() {
        return calculatedPower;
    }

    public Throwable getCalculationError() {
        return calculationError;
    }
}
