package org.study.bootcamp.task_38.army_of_heroes.v1;

import java.util.ArrayList;
import java.util.List;

public class Squad {

    private String name;
    private List<Unit> units = new ArrayList<>();

    public Squad(String name, List<Unit> units) {
        this.name = name;
        this.units.addAll(units);
    }

    public int calculateSquadPower() {
        int total = 0;
        for (Unit unit : units) {
            total += unit.getPower();
        }
        return total;
    }

    public String getName() {
        return name;
    }
}
