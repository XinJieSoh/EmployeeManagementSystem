package com.soh.EmployeeManagementSystem.Controllers;

import com.soh.EmployeeManagementSystem.DTOs.ProjectDTO;
import com.soh.EmployeeManagementSystem.Models.Employee;
import com.soh.EmployeeManagementSystem.Models.Project;
import com.soh.EmployeeManagementSystem.DTOs.ProjectResponse;
import com.soh.EmployeeManagementSystem.Repositories.EmployeeRepository;
import com.soh.EmployeeManagementSystem.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private ProjectResponse convertToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getProjectId());
        response.setName(project.getName());
        response.setEmployees(project.getEmployees());
        return response;
    }

    @GetMapping("/projects")
    public List<ProjectResponse> getProjects() {
        return projectRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(proj -> ResponseEntity.ok(convertToResponse(proj)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/project")
    public ProjectResponse createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        Set<Employee> employees = projectDTO.getEmployeeIds().stream()
                .map(employeeRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        employees.forEach(project::addEmployee);

        Project savedProject = projectRepository.save(project);
        return convertToResponse(savedProject);
    }

    @PutMapping("project/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable long id, @RequestBody ProjectDTO projectUpdates) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project existingProject = project.get();
            if (projectUpdates.getName() != null) {
                existingProject.setName(projectUpdates.getName());
            }

            if (projectUpdates.getEmployeeIds() != null) {
                Set<Employee> employees = projectUpdates.getEmployeeIds().stream()
                        .map(employeeRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());

                existingProject.setEmployees(employees);
            }

            Project updatedProject = projectRepository.save(existingProject);
            return ResponseEntity.ok(convertToResponse(updatedProject));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project existingProject = project.get();
            // Collect employees to be removed to avoid ConcurrentModificationException
            Set<Employee> employeesToRemove = new HashSet<>(existingProject.getEmployees());

            // Remove the project from each employee
            for (Employee employee : employeesToRemove) {
                employee.removeProject(existingProject);
            }

            // Clear the employees from the project
            existingProject.getEmployees().clear();
            projectRepository.delete(existingProject);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
