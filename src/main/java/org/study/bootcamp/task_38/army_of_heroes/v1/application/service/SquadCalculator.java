package org.study.bootcamp.task_38.army_of_heroes.v1.application.service;

import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Squad;

public class SquadCalculator extends Thread {

    private final Squad squad;
    private int result;

    public SquadCalculator(Squad squad) {
        this.squad = squad;
    }

    @Override
    public void run() {
        result = squad.calculateSquadPower();
        System.out.println("Отряд " + squad.getName() + ": сила = " + result);
    }

    public int getResult() {
        return result;
    }
}
