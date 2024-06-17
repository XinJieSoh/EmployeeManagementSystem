package com.soh.EmployeeManagementSystem.Controllers;

import com.soh.EmployeeManagementSystem.DTOs.EmployeeDTO;
import com.soh.EmployeeManagementSystem.Models.Department;
import com.soh.EmployeeManagementSystem.Models.Employee;
import com.soh.EmployeeManagementSystem.Models.EmployeeResponse;
import com.soh.EmployeeManagementSystem.Models.Project;
import com.soh.EmployeeManagementSystem.Repositories.DepartmentRepository;
import com.soh.EmployeeManagementSystem.Repositories.EmployeeRepository;
import com.soh.EmployeeManagementSystem.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private EmployeeResponse convertToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setPosition(employee.getPosition());
        response.setDepartmentId(employee.getDepartment().getDepartmentId());
        response.setProjects(employee.getProjects());
        return response;
    }

    @GetMapping("/employees")
    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(emp -> ResponseEntity.ok(convertToResponse(emp)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/employee")
    public EmployeeResponse createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setPosition(employeeDTO.getPosition());

        Optional<Department> department = departmentRepository.findById(employeeDTO.getDepartmentId());
        department.ifPresent(employee::setDepartment);

        Set<Project> projects = employeeDTO.getProjectIds().stream()
                .map(projectRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        projects.forEach(employee::addProject);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToResponse(savedEmployee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeUpdates) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            if (employeeUpdates.getName() != null) {
                existingEmployee.setName(employeeUpdates.getName());
            }
            if (employeeUpdates.getPosition() != null) {
                existingEmployee.setPosition(employeeUpdates.getPosition());
            }
            if (employeeUpdates.getDepartmentId() != null) {
                Optional<Department> department = departmentRepository.findById(employeeUpdates.getDepartmentId());
                department.ifPresent(existingEmployee::setDepartment);
            }
            if (employeeUpdates.getProjectIds() != null) {
                Set<Project> newProjects = employeeUpdates.getProjectIds().stream()
                        .map(projectRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());

                Set<Project> currentProjects = existingEmployee.getProjects();
                currentProjects.forEach(existingEmployee::removeProject);
                newProjects.forEach(existingEmployee::addProject);
            }

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(convertToResponse(updatedEmployee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.getProjects().forEach(project -> project.removeEmployee(existingEmployee));
            employeeRepository.delete(existingEmployee);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
