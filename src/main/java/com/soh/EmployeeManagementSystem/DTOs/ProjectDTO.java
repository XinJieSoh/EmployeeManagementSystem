package com.soh.EmployeeManagementSystem.DTOs;

import java.util.Set;

public class ProjectDTO {
    private String name;
    private Set<Long> employeeIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

}
