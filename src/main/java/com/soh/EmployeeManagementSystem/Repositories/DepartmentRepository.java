package com.soh.EmployeeManagementSystem.Repositories;

import com.soh.EmployeeManagementSystem.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
