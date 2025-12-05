package org.study.bootcamp.task_38.army_of_heroes.v1.application.service;

import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Squad;

import java.util.ArrayList;
import java.util.List;

public class ArmyPowerCalculator {

    private final List<Squad> squads = new ArrayList<>();

    public void addSquad(Squad squad) {
        squads.add(squad);
    }

    public int calculateArmyPower() {
        List<SquadPowerCalculator> calculators = startSquadPowerCalculators();
        waitForAllCalculators(calculators);
        return sumResultsOrThrow(calculators);
    }

    private List<SquadPowerCalculator> startSquadPowerCalculators() {
        List<SquadPowerCalculator> calculators = new ArrayList<>();

        for (Squad squad : squads) {
            SquadPowerCalculator calculator = new SquadPowerCalculator(squad);
            calculators.add(calculator);
            calculator.start();
        }

        return calculators;
    }

    private void waitForAllCalculators(List<SquadPowerCalculator> calculators) {
        try {
            for (SquadPowerCalculator calculator : calculators) {
                calculator.join();
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();

            for (SquadPowerCalculator calculator : calculators) {
                calculator.interrupt();
            }

            throw new IllegalStateException("ожидание завершения потоков прервано", interruptedException);
        }
    }

    private int sumResultsOrThrow(List<SquadPowerCalculator> calculators) {
        int totalArmyPower = 0;

        for (SquadPowerCalculator calculator : calculators) {
            Throwable calculationError = calculator.getCalculationError();
            if (calculationError != null) {
                throw new RuntimeException(
                        "ошибка расчёта силы отряда: " + calculator.getName(),
                        calculationError
                );
            }

            totalArmyPower += calculator.getCalculatedPower();
        }

        return totalArmyPower;
    }
}
