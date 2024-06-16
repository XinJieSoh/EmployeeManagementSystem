package com.soh.EmployeeManagementSystem.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @GetMapping("/")
    public String getPage() {
        return "<h1>Welcome to Employee Management System!</h1> <br/><p>For the API documentation, go to this link <a href=\"http://localhost:8080/swagger-ui/index.html\">http://localhost:8080/swagger-ui/index.html</a>.</p>";
    }
}
