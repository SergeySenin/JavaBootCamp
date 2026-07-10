package org.study.bootcamp.mishustin.task3.rpg.abstraction.v1.api.cli;

import org.study.bootcamp.mishustin.task3.rpg.abstraction.v1.domain.model.Archer;
import org.study.bootcamp.mishustin.task3.rpg.abstraction.v1.domain.model.Character;
import org.study.bootcamp.mishustin.task3.rpg.abstraction.v1.domain.model.Warrior;

public class Demo {
    public static void main(String[] args) {

        Character warrior = new Warrior("Warrior");
        Character archer = new Archer("Archer");

        System.out.println("=== Battle Start ===");
        System.out.println(warrior.getName() + " (health=" + warrior.getHealth() + ")");
        System.out.println(archer.getName() + " (health=" + archer.getHealth() + ")");
        System.out.println();

        warrior.attack(archer);
        System.out.println(archer.getName() + " health: " + archer.getHealth());

        archer.attack(warrior);
        System.out.println(warrior.getName() + " health: " + warrior.getHealth());

        warrior.attack(archer);
        System.out.println(archer.getName() + " health: " + archer.getHealth());

        archer.attack(warrior);
        System.out.println(warrior.getName() + " health: " + warrior.getHealth());

        System.out.println("\n=== Battle End ===");
    }
}
