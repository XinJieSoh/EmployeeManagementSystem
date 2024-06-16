package com.soh.EmployeeManagementSystem.DTOs;

public class EmployeeDTO {
    private String name;
    private String position;
    private Long id_department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getId_department() {
        return id_department;
    }

    public void setId_department(Long id_department) {
        this.id_department = id_department;
    }
}
