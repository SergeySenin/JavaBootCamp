package org.study.bootcamp.task_18.lotr_rpg.v1.application.service;

import org.study.bootcamp.task_18.lotr_rpg.v1.domain.model.Character;
import org.study.bootcamp.task_18.lotr_rpg.v1.domain.model.Item;

import java.util.*;
import java.util.function.*;

public final class InventoryManager {

    public void addItem(org.study.bootcamp.task_18.lotr_rpg.v1.domain.model.Character character, Item item, Consumer<Item> onAdd) {
        Objects.requireNonNull(character, "character: must not be null");
        Objects.requireNonNull(item, "item: must not be null");
        Objects.requireNonNull(onAdd, "onAdd: must not be null");

        character.getInventory().add(item);
        onAdd.accept(item);
    }

    public void removeItem(org.study.bootcamp.task_18.lotr_rpg.v1.domain.model.Character character, Predicate<Item> condition) {
        Objects.requireNonNull(character, "character: must not be null");
        Objects.requireNonNull(condition, "condition: must not be null");

        character.getInventory().removeIf(condition);
    }

    public void updateItem(Character character,
                           Predicate<Item> condition,
                           Function<Item, Item> updater) {
        Objects.requireNonNull(character, "character: must not be null");
        Objects.requireNonNull(condition, "condition: must not be null");
        Objects.requireNonNull(updater, "updater: must not be null");

        List<Item> inventory = character.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            Item current = inventory.get(i);
            if (condition.test(current)) {
                inventory.set(i, updater.apply(current));
            }
        }
    }
}
