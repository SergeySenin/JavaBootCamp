package org.study.bootcamp.mishustin.task55.mice.are.very.nice.v1.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final String name;
    private final List<Food> foods = new ArrayList<>();

    public Room(String name, List<Food> foods) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название комнаты не должно быть пустым");
        }
        if (foods == null) {
            throw new IllegalArgumentException("Список еды не должен быть null");
        }

        this.name = name;
        this.foods.addAll(foods);
    }

    public String getName() {
        return name;
    }

    boolean isEmpty() {
        return foods.isEmpty();
    }

    int drainFoodsTo(List<Food> target) {
        int count = foods.size();
        if (count == 0) {
            return 0;
        }

        target.addAll(foods);
        foods.clear();
        return count;
    }
}
