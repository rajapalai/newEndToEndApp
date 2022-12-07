package com.employeeservice.employeeappnew;


import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import com.employeeservice.employeeappnew.util.ServiceResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int port;

    private String baseURL = "http://localhost";

    private static TestRestTemplate restTemplate;

    @Autowired
    private TestH2Repository testH2Repository;

    private EmployeeRequestDTO employeeRequestDTO;

    private EmployeeResponseDTO employeeResponseDTO;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseURL = baseURL.concat(":").concat(port + "").concat("/employee-service");
    }

    @Test
    public void test_onboardNewEmployee() {

        employeeRequestDTO = new EmployeeRequestDTO("sivam","sambhu",50000,"sivam@gmail.com","1478963587","DEVOPS");


        ResponseEntity<EmployeeResponseDTO> employeeResponseDTO = restTemplate.postForEntity(baseURL,employeeRequestDTO, EmployeeResponseDTO.class);
        assertEquals(HttpStatus.OK,employeeResponseDTO.getStatusCode());

        assertEquals(employeeResponseDTO,employeeResponseDTO.getBody());
        assertEquals(1,testH2Repository.findAll().size());
    }
}
