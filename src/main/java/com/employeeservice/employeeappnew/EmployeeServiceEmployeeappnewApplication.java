package com.employeeservice.employeeappnew;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "employee_api",version = "3.0",description = "Employee microservice"))
public class EmployeeServiceEmployeeappnewApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceEmployeeappnewApplication.class, args);
	}

}
