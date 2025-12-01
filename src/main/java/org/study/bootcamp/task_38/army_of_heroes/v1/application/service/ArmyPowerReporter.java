package org.study.bootcamp.task_38.army_of_heroes.v1.application.service;

import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Squad;

import java.util.ArrayList;
import java.util.List;

public class ArmyPowerReporter {

    private final List<Squad> squads = new ArrayList<>();

    public void addSquad(Squad squad) {
        squads.add(squad);
    }

    public int calculateArmyPower() throws InterruptedException {
        List<SquadPowerReporter> allReporters = new ArrayList<>();

        for (Squad currentSquad : squads) {
            SquadPowerReporter squadReporter = new SquadPowerReporter(currentSquad);
            allReporters.add(squadReporter);
            squadReporter.start();
        }

        try {
            for (SquadPowerReporter waitingReporter : allReporters) {
                waitingReporter.join();
            }
        } catch (InterruptedException exception) {
            for (SquadPowerReporter aliveReporter : allReporters) {
                if (aliveReporter.isAlive()) {
                    aliveReporter.interrupt();
                }
            }
            throw exception;
        }

        int totalPower = 0;
        for (SquadPowerReporter resultReporter : allReporters) {
            totalPower += resultReporter.getResult();
        }

        return totalPower;
    }
}
