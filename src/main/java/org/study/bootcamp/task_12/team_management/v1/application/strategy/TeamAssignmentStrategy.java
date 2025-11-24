package org.study.bootcamp.task_12.team_management.v1.application.strategy;

import org.study.bootcamp.task_12.team_management.v1.domain.model.Employee;
import org.study.bootcamp.task_12.team_management.v1.domain.model.Project;

import java.util.*;

public interface TeamAssignmentStrategy {
    List<Employee> assignTeam(Project project, List<Employee> employees);
}
