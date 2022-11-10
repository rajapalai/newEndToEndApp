package com.employeeservice.employeeappnew.exception;

public class EmployeeServiceBusinessException extends RuntimeException {
    private String message;

    public EmployeeServiceBusinessException(String message) {
        this.message = message;
    }

}
