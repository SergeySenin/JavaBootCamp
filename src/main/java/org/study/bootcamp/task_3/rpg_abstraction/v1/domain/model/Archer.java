package org.study.bootcamp.task_3.rpg_abstraction.v1.domain.model;

public class Archer extends Character {

    public Archer(String name) {
        super(name, 10, 5, 3);
    }

    @Override
    public void attack(Character target) {
        if (!target.isAlive()) {
            System.out.println(getName() + " cannot attack "
                    + target.getName() + " because he is already defeated!");
            return;
        }
        System.out.println(getName() + " shoots at "
                + target.getName() + " with dexterity " + getAgility());
        target.applyDamage(getAgility());
    }
}
