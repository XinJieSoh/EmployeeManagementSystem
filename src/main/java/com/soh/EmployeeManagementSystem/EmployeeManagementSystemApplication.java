package com.soh.EmployeeManagementSystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Employee Management System",
				version = "1.0.0",
				description ="The objective of this project is to create a simple yet comprehensive Employee Management System using Java with Spring Boot. The system will manage employees across different departments within a company. It will utilize a relational database to store employee details, department information, and project assignments. This system will expose a RESTful API for performing CRUD operations on the employee data.",
				contact = @Contact(
						name ="Soh Xin Jie",
						email = "sohxinjie518@gmail.com"
				)
		)
)
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

}
