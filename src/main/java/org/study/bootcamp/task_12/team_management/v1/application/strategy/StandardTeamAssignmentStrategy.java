package org.study.bootcamp.task_12.team_management.v1.application.strategy;

import org.study.bootcamp.task_12.team_management.v1.domain.model.Employee;
import org.study.bootcamp.task_12.team_management.v1.domain.model.Project;

import java.util.*;

public final class StandardTeamAssignmentStrategy implements TeamAssignmentStrategy {

    @Override
    public List<Employee> assignTeam(Project project, List<Employee> employees) {
        Objects.requireNonNull(project, "project: must not be null");
        Objects.requireNonNull(employees, "employees: must not be null");

        Set<String> remainingSkills = prepareRemainingSkills(project);
        if (remainingSkills.isEmpty() || employees.isEmpty()) {
            return Collections.emptyList();
        }

        List<Employee> selectedTeam = new ArrayList<>();

        for (Employee candidate : employees) {
            if (!shouldConsiderCandidate(candidate, remainingSkills)) {
                continue;
            }

            Set<String> newlyCovered = coveredSkills(candidate, remainingSkills);
            if (!newlyCovered.isEmpty()) {
                includeCandidate(candidate, newlyCovered, selectedTeam, remainingSkills);
                if (allSkillsCovered(remainingSkills)) break;
            }
        }

        return selectedTeam;
    }

    private static Set<String> prepareRemainingSkills(Project project) {
        return new LinkedHashSet<>(project.getRequiredSkills());
    }

    private static boolean shouldConsiderCandidate(Employee candidate, Set<String> remainingSkills) {
        return candidate != null && !remainingSkills.isEmpty() && candidate.hasAnyOf(remainingSkills);
    }

    private static Set<String> coveredSkills(Employee employee, Set<String> remainingSkills) {
        Set<String> overlap = new HashSet<>(employee.skills());
        overlap.retainAll(remainingSkills);
        return overlap;
    }

    private static void includeCandidate(
            Employee candidate,
            Set<String> newlyCovered,
            List<Employee> selectedTeam,
            Set<String> remainingSkills
    ) {
        selectedTeam.add(candidate);
        remainingSkills.removeAll(newlyCovered);
    }

    private static boolean allSkillsCovered(Set<String> remainingSkills) {
        return remainingSkills.isEmpty();
    }
}
