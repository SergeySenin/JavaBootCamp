package org.study.bootcamp.task_12.team_management.v1.domain.model;

import java.util.*;

public record Employee(int id, String name, Set<String> skills) {

    public Employee(int id, String name, Set<String> skills) {
        validate(id, name, skills);

        this.id = id;
        this.name = name.trim();
        this.skills = Set.copyOf(skills);
    }

    public boolean hasSkill(String skill) {
        if (skill == null || skill.isBlank()) {
            return false;
        }
        return skills.contains(skill.trim());
    }

    public boolean hasAnyOf(Set<String> requiredSkills) {
        if (requiredSkills == null || requiredSkills.isEmpty()) {
            return false;
        }
        for (String required : requiredSkills) {
            if (skills.contains(required)) {
                return true;
            }
        }
        return false;
    }

    private static void validate(int id, String name, Set<String> skills) {
        if (id <= 0) {
            throw new IllegalArgumentException("id: must be > 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null/blank");
        }
        if (skills == null) {
            throw new IllegalArgumentException("skills: must not be null");
        }
        for (String skill : skills) {
            if (skill == null || skill.isBlank()) {
                throw new IllegalArgumentException("skills: must not contain null/blank elements");
            }
        }
    }
}
