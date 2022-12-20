package com.employeeservice.employeeappnew.exception;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Integer fieldValue;
    private EmployeeRequestDTO employeeRequestDTO;
    private HttpStatus httpStatus;


    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName, EmployeeRequestDTO employeeRequestDTO,HttpStatus httpStatus){
        super(String.format("%s data could not be onboarded with : '%s' Request type : '%s'",resourceName,employeeRequestDTO,httpStatus));
        this.resourceName = resourceName;
        this.employeeRequestDTO = employeeRequestDTO;
        this.httpStatus = httpStatus;
    }
    public ResourceNotFoundException() {

    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Integer getFieldValue() {
        return fieldValue;
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
