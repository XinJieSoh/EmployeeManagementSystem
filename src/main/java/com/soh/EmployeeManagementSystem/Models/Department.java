package com.soh.EmployeeManagementSystem.Models;

import jakarta.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id_department;

    @Column
    private String name;

    public long getId_department() {
        return id_department;
    }

    public void setId_department(long id_department) {
        this.id_department = id_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
