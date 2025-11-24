package org.study.bootcamp.task_12.team_management.v1.application.service;

import lombok.Setter;

import org.study.bootcamp.task_12.team_management.v1.domain.model.Employee;
import org.study.bootcamp.task_12.team_management.v1.domain.model.Project;
import org.study.bootcamp.task_12.team_management.v1.application.strategy.TeamAssignmentStrategy;

import java.util.*;

public final class ProjectManager {

    private final Map<Integer, Employee> employeesById = new LinkedHashMap<>();
    private final Map<Integer, Project>  projectsById  = new LinkedHashMap<>();

    @Setter
    private TeamAssignmentStrategy assignmentStrategy;

    public void addEmployee(Employee employee) {
        requireNonNull(employee, "employee");
        int id = employee.id();
        if (employeesById.containsKey(id)) {
            throw new IllegalArgumentException("employee id already exists: " + id);
        }
        employeesById.put(id, employee);
    }

    public void addProject(Project project) {
        requireNonNull(project, "project");
        int projectId = project.getProjectId();
        if (projectsById.containsKey(projectId)) {
            throw new IllegalArgumentException("project id already exists: " + projectId);
        }
        projectsById.put(projectId, project);
    }

    public void assignTeamToProject(int projectId) {
        Project project = getExistingProject(projectId);

        if (assignmentStrategy == null) {
            return;
        }

        List<Employee> availableEmployees = new ArrayList<>(employeesById.values());
        List<Employee> selectedTeam = assignmentStrategy.assignTeam(project, availableEmployees);

        project.replaceTeam(selectedTeam);
    }

    public List<Employee> getTeamForProject(int projectId) {
        Project project = getExistingProject(projectId);
        return List.copyOf(project.getTeamMembers());
    }

    public List<Project> findProjectsForEmployee(Employee employee) {
        requireNonNull(employee, "employee");
        List<Project> suitable = new ArrayList<>();
        for (Project project : projectsById.values()) {
            if (employee.skills().containsAll(project.getRequiredSkills())) {
                suitable.add(project);
            }
        }
        return suitable;
    }

    public boolean assignEmployeeToProject(int projectId, Employee employee) {
        Project project = getExistingProject(projectId);
        requireNonNull(employee, "employee");
        if (!employee.skills().containsAll(project.getRequiredSkills())) {
            return false;
        }
        return project.addTeamMember(employee);
    }

    public boolean removeEmployeeFromProject(int projectId, int employeeId) {
        Project project = getExistingProject(projectId);
        return project.removeTeamMemberById(employeeId);
    }

    public List<Employee> getTeamMembers(int projectId) {
        return getTeamForProject(projectId);
    }

    public void removeIneligibleEmployees(Project project) {
        requireNonNull(project, "project");
        Set<String> requiredSkills = project.getRequiredSkills();
        project.removeTeamMembersIf(member -> !member.skills().containsAll(requiredSkills));
    }

    private Project getExistingProject(int projectId) {
        Project project = projectsById.get(projectId);
        if (project == null) {
            throw new IllegalArgumentException("project not found: id=" + projectId);
        }
        return project;
    }

    private static void requireNonNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + ": must not be null");
        }
    }

    public List<Employee> allEmployees() {
        return List.copyOf(employeesById.values());
    }

    public List<Project> allProjects() {
        return List.copyOf(projectsById.values());
    }
}
