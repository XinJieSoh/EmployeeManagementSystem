package com.soh.EmployeeManagementSystem.Controllers;

import com.soh.EmployeeManagementSystem.DTOs.EmployeeDTO;
import com.soh.EmployeeManagementSystem.Models.Department;
import com.soh.EmployeeManagementSystem.Models.Employee;
import com.soh.EmployeeManagementSystem.Repositories.DepartmentRepository;
import com.soh.EmployeeManagementSystem.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{id}")
    public Object getEmployeeById(@PathVariable long id) {
        return employeeRepository.findById(id);
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setPosition(employeeDTO.getPosition());

        Optional<Department> department = departmentRepository.findById(employeeDTO.getId_department());
        department.ifPresent(employee::setDepartment);
        return employeeRepository.save(employee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeUpdates) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            if(employeeUpdates.getName() != null) {
                existingEmployee.setName(employeeUpdates.getName());
            }
            if(employeeUpdates.getPosition() != null) {
                existingEmployee.setPosition(employeeUpdates.getPosition());
            }
            if(employeeUpdates.getId_department() != null) {
                Optional<Department> department = departmentRepository.findById(employeeUpdates.getId_department());
                department.ifPresent(existingEmployee::setDepartment);
            }

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
