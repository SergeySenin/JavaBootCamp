package org.study.bootcamp.task_55.mice_are_very_nice.v1.domain.model;

public class Food {

    private final String name;

    public Food(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название еды не должно быть пустым");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
