package com.employeeservice.employeeappnew.exception;

import com.employeeservice.employeeappnew.util.ServiceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler  extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeServiceBusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<?> handleServiceResponse(EmployeeServiceBusinessException exception){
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorDetails> errorDetails = new ArrayList<>();
        errorDetails.add(new ErrorDetails(exception.getMessage()));
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setErrorDetails(errorDetails);
        return serviceResponse;
    }

    // Handle Exception Globally
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception exception, WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(), webRequest.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
