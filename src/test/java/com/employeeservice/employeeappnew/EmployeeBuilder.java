package com.employeeservice.employeeappnew;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;

public class EmployeeBuilder {

    public static EmployeeResponseDTO employeeResponseDTO() {
      //  Date date = new Date("20-10-2022");
        return new EmployeeResponseDTO(10,"Smaranika","Pattanayak",50000,"liza@gmail.com","5789654789","DEVOPS");
    }

    public static EmployeeRequestDTO employeeRequestDTO() {
        //  Date date = new Date("20-10-2022");
        return new EmployeeRequestDTO("Smaranika","Pattanayak",50000,"liza@gmail.com","5789654789","DEVOPS");
    }
    public static EmployeeEntity employeeEntity() {
       // Date date = new Date("20-10-2022");
        return new EmployeeEntity(10,"Smaranika","Pattanayak",50000,"liza@gmail.com","5789654789","DEVOPS");
    }
}

