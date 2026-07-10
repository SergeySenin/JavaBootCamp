package org.study.bootcamp.mishustin.task46.witcher.magic.map.v1.domain.model;

public class Monster {

    private final String name;
    private final Location location;

    public Monster(String name, Location location) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название монстра не должно быть пустым");
        }
        if (location == null) {
            throw new IllegalArgumentException("Местоположение монстра не должно быть null");
        }

        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
