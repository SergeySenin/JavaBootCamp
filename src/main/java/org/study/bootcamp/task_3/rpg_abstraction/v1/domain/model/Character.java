package org.study.bootcamp.task_3.rpg_abstraction.v1.domain.model;

import lombok.Getter;

@Getter
public abstract class Character {

    protected String name;
    protected int health = 100;
    protected int agility;
    protected int intelligence;
    protected int strength;

    public Character(String name) {
        validateName(name);

        this.name = name;
        this.agility = 5;
        this.intelligence = 5;
        this.strength = 5;
    }

    public Character(String name, int agility, int intelligence, int strength) {
        validateName(name);

        this.name = name;
        this.agility = agility;
        this.intelligence = intelligence;
        this.strength = strength;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Character name cannot be null or blank!");
        }
    }

    public abstract void attack(Character target);

    public boolean isAlive() {
        return this.health > 0;
    }

    public void applyDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }
}
