package org.study.bootcamp.task_12.team_management.v1.application.strategy;

import org.study.bootcamp.task_12.team_management.v1.domain.model.Employee;
import org.study.bootcamp.task_12.team_management.v1.domain.model.Project;

import java.util.*;

public final class BalancedTeamAssignmentStrategy implements TeamAssignmentStrategy {

    private final Map<Employee, Integer> projectCountByEmployee;
    private final int maxProjectsPerEmployee;

    public BalancedTeamAssignmentStrategy(Map<Employee, Integer> projectCountByEmployee, int maxProjectsPerEmployee) {
        this.projectCountByEmployee = Objects.requireNonNull(
                projectCountByEmployee,
                "projectCountByEmployee: must not be null"
        );
        if (maxProjectsPerEmployee <= 0) {
            throw new IllegalArgumentException("maxProjectsPerEmployee: must be > 0");
        }
        this.maxProjectsPerEmployee = maxProjectsPerEmployee;
    }

    public BalancedTeamAssignmentStrategy(Map<Employee, Integer> projectCountByEmployee) {
        this(projectCountByEmployee, 3);
    }

    @Override
    public List<Employee> assignTeam(Project project, List<Employee> employees) {
        Objects.requireNonNull(project, "project: must not be null");
        Objects.requireNonNull(employees, "employees: must not be null");

        Set<String> remainingSkills = prepareRemainingSkills(project);
        if (remainingSkills.isEmpty() || employees.isEmpty()) {
            return Collections.emptyList();
        }

        List<Employee> candidates = new ArrayList<>(employees);
        candidates.removeIf(Objects::isNull);
        candidates.sort((a, b) -> {
            int loadA = currentLoad(a);
            int loadB = currentLoad(b);
            if (loadA != loadB) return Integer.compare(loadA, loadB);

            int coverA = coverageCount(a, remainingSkills);
            int coverB = coverageCount(b, remainingSkills);
            return Integer.compare(coverB, coverA);
        });

        List<Employee> selectedTeam = new ArrayList<>();

        for (Employee candidate : candidates) {
            if (!canConsider(candidate, remainingSkills)) continue;

            int currentLoad = currentLoad(candidate);
            if (currentLoad >= maxProjectsPerEmployee) continue;

            Set<String> newlyCovered = coveredSkills(candidate, remainingSkills);
            if (newlyCovered.isEmpty()) continue;

            selectedTeam.add(candidate);
            remainingSkills.removeAll(newlyCovered);
            incrementLoad(candidate);

            if (remainingSkills.isEmpty()) break;
        }

        return selectedTeam;
    }

    private static Set<String> prepareRemainingSkills(Project project) {
        return new LinkedHashSet<>(project.getRequiredSkills());
    }

    private boolean canConsider(Employee employee, Set<String> remainingSkills) {
        return employee != null && !remainingSkills.isEmpty() && employee.hasAnyOf(remainingSkills);
    }

    private static Set<String> coveredSkills(Employee employee, Set<String> remainingSkills) {
        Set<String> overlap = new HashSet<>(employee.skills());
        overlap.retainAll(remainingSkills);
        return overlap;
    }

    private static int coverageCount(Employee employee, Set<String> remainingSkills) {
        int count = 0;
        for (String string : remainingSkills) {
            if (employee.skills().contains(string)) count++;
        }
        return count;
    }

    private int currentLoad(Employee employee) {
        return projectCountByEmployee.getOrDefault(employee, 0);
    }

    private void incrementLoad(Employee employee) {
        projectCountByEmployee.merge(employee, 1, Integer::sum);
    }
}
