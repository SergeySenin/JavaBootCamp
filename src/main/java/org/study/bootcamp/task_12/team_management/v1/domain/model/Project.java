package org.study.bootcamp.task_12.team_management.v1.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.function.Predicate;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Project {

    @EqualsAndHashCode.Include
    private final int projectId;
    private final String name;
    private final Set<String> requiredSkills;
    private final List<Employee> teamMembers;

    public Project(int projectId, String name, Set<String> requiredSkills) {
        validate(projectId, name, requiredSkills);

        this.projectId = projectId;
        this.name = name.trim();
        this.requiredSkills = Set.copyOf(requiredSkills);
        this.teamMembers = new ArrayList<>();
    }

    public boolean addTeamMember(Employee employee) {
        Objects.requireNonNull(employee, "employee: must not be null");
        if (teamMembers.contains(employee)) {
            return false;
        }
        return teamMembers.add(employee);
    }

    public boolean removeTeamMemberById(int employeeId) {
        return teamMembers.removeIf(employee -> employee.id() == employeeId);
    }

    public void replaceTeam(List<Employee> newTeam) {
        if (newTeam == null) {
            throw new IllegalArgumentException("newTeam: must not be null");
        }
        teamMembers.clear();
        for (Employee member : newTeam) {
            if (member == null) continue;
            if (!teamMembers.contains(member)) {
                teamMembers.add(member);
            }
        }
    }

    public void removeTeamMembersIf(Predicate<Employee> predicate) {
        Objects.requireNonNull(predicate, "predicate: must not be null");
        teamMembers.removeIf(predicate);
    }

    public void clearTeam() {
        teamMembers.clear();
    }

    public List<Employee> getTeamMembers() {
        return List.copyOf(teamMembers);
    }

    private static void validate(int projectId, String name, Set<String> requiredSkills) {
        if (projectId <= 0) {
            throw new IllegalArgumentException("projectId: must be > 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name: must not be null/blank");
        }
        if (requiredSkills == null) {
            throw new IllegalArgumentException("requiredSkills: must not be null");
        }
        for (String skill : requiredSkills) {
            if (skill == null || skill.isBlank()) {
                throw new IllegalArgumentException("requiredSkills: must not contain null/blank elements");
            }
        }
    }
}
