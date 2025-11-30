package org.study.bootcamp.task_38.army_of_heroes.v1;

import java.util.ArrayList;
import java.util.List;

public class Army {

    private List<Squad> squads = new ArrayList<>();

    public void addSquad(Squad squad) {
        squads.add(squad);
    }

    public int calculateTotalPower() throws InterruptedException {
        List<SquadCalculator> calculators = new ArrayList<>();

        for (Squad squad : squads) {
            SquadCalculator calculator = new SquadCalculator(squad);
            calculators.add(calculator);
            calculator.start();
        }

        for (SquadCalculator calculator : calculators) {
            calculator.join();
        }

        int totalPower = 0;
        for (SquadCalculator calculator : calculators) {
            totalPower += calculator.getResult();
        }

        return totalPower;
    }
}
