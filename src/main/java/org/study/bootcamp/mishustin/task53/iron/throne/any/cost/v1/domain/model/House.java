package org.study.bootcamp.mishustin.task53.iron.throne.any.cost.v1.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class House {

    private final Object lock = new Object();

    private final String name;
    private final List<String> availableRoles;
    private final Map<User, String> assignedRolesByUser = new HashMap<>();

    public House(String name, List<String> roles) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название дома не должно быть пустым");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Список ролей не должен быть пустым");
        }

        LinkedHashSet<String> uniqueRoles = new LinkedHashSet<>();
        for (String role : roles) {
            if (role == null || role.isBlank()) {
                throw new IllegalArgumentException("Роль не должна быть пустой");
            }
            uniqueRoles.add(role);
        }

        this.name = name;
        this.availableRoles = new ArrayList<>(uniqueRoles);
    }

    public String getName() {
        return name;
    }

    public String assignRole(User user) throws InterruptedException {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не должен быть null");
        }

        synchronized (lock) {
            if (assignedRolesByUser.containsKey(user)) {
                throw new IllegalStateException("Пользователь уже состоит в доме: " + user.getName());
            }

            while (availableRoles.isEmpty()) {
                System.out.printf(
                        "%s ждёт свободную роль в доме '%s'%n",
                        user.getName(),
                        name
                );
                lock.wait();
            }

            String role = availableRoles.remove(0);
            assignedRolesByUser.put(user, role);

            System.out.printf(
                    "%s получил роль '%s' в доме '%s'%n",
                    user.getName(),
                    role,
                    name
            );

            return role;
        }
    }

    public void releaseRole(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не должен быть null");
        }

        synchronized (lock) {
            String role = assignedRolesByUser.remove(user);
            if (role == null) {
                throw new IllegalStateException("Пользователь не имеет роли в доме: " + user.getName());
            }

            availableRoles.add(role);

            System.out.printf(
                    "%s освободил роль '%s' в доме '%s'%n",
                    user.getName(),
                    role,
                    name
            );

            lock.notifyAll();
        }
    }
}
