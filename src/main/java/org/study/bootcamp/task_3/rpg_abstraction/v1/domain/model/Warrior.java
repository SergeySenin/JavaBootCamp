package org.study.bootcamp.task_3.rpg_abstraction.v1.domain.model;

public class Warrior extends Character {

    public Warrior(String name) {
        super(name, 5, 3, 10);
    }

    @Override
    public void attack(Character target) {
        if (!target.isAlive()) {
            System.out.println(getName() + " cannot attack "
                    + target.getName() + " because he is already defeated!");
            return;
        }
        System.out.println(getName() + " strikes "
                + target.getName() + " with strength " + getStrength());
        target.applyDamage(getStrength());
    }
}
