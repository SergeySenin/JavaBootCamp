package org.study.bootcamp.mishustin.task53.iron.throne.any.cost.v1.domain.model;

public class User {

    private final String name;

    private House house;
    private String assignedRole;

    public User(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не должно быть пустым");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAssignedRole() {
        return assignedRole;
    }

    public void joinHouse(House house) {
        if (house == null) {
            throw new IllegalArgumentException("Дом не должен быть null");
        }
        if (this.house != null) {
            throw new IllegalStateException("Пользователь уже состоит в доме: " + this.house.getName());
        }

        try {
            String role = house.assignRole(this);
            this.house = house;
            this.assignedRole = role;
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            System.out.printf(
                    "%s прерван во время ожидания роли в доме '%s'%n",
                    name,
                    house.getName()
            );
        }
    }

    public void leaveHouse() {
        if (house == null || assignedRole == null) {
            throw new IllegalStateException("Пользователь не состоит в доме и не может освободить роль: " + name);
        }

        House currentHouse = house;

        currentHouse.releaseRole(this);

        house = null;
        assignedRole = null;
    }
}
