package com.employeeservice.employeeappnew.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

public class JwtValidateApiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public JwtValidateApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
