package com.soh.EmployeeManagementSystem.Repositories;

import com.soh.EmployeeManagementSystem.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
