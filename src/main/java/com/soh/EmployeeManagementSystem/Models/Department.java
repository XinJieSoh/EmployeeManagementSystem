package com.soh.EmployeeManagementSystem.Models;

import jakarta.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long departmentId;

    @Column
    private String name;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
