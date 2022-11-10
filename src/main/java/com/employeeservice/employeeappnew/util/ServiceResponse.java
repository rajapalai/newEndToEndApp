package com.employeeservice.employeeappnew.util;

import com.employeeservice.employeeappnew.exception.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {

    private HttpStatus httpStatus;
    private T responsePayload;

    private List<ErrorDetails> errorDetails;

    public ServiceResponse(HttpStatus httpStatus, T responsePayload) {
        this.httpStatus = httpStatus;
        this.responsePayload = responsePayload;
    }
}
