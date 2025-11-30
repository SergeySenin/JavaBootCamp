package org.study.bootcamp.task_38.army_of_heroes.v1;

public class SquadCalculator extends Thread {

    private Squad squad;
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
