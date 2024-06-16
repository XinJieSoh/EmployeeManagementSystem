package com.soh.EmployeeManagementSystem.Repositories;

import com.soh.EmployeeManagementSystem.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
