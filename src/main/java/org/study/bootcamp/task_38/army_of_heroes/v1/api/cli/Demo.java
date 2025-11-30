package org.study.bootcamp.task_38.army_of_heroes.v1.api.cli;

import org.study.bootcamp.task_38.army_of_heroes.v1.application.service.Army;
import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Archer;
import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Mage;
import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Squad;
import org.study.bootcamp.task_38.army_of_heroes.v1.domain.model.Swordsman;

import java.util.List;

public class Demo {
    public static void main(String[] args) throws InterruptedException {

        Squad archers = new Squad("Лучники", List.of(new Archer(30), new Archer(25)));
        Squad swordsmen = new Squad("Мечники", List.of(new Swordsman(40), new Swordsman(35)));
        Squad mages = new Squad("Маги", List.of(new Mage(50), new Mage(45)));

        Army army = new Army();
        army.addSquad(archers);
        army.addSquad(swordsmen);
        army.addSquad(mages);

        int totalPower = army.calculateTotalPower();
        System.out.println("Общая сила армии: " + totalPower);
    }
}
