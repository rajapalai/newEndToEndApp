package com.employeeservice.employeeappnew.controller;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.service.EmployeeService;
import com.employeeservice.employeeappnew.util.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee-service")
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ServiceResponse<EmployeeResponseDTO> addEmployeeOnboardData(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try{
            EmployeeResponseDTO newEmployee = employeeService.onboardNewEmployee(employeeRequestDTO);
            employeeServiceResponse.setHttpStatus(HttpStatus.CREATED);
            employeeServiceResponse.setResponsePayload(newEmployee);
            logger.info("user created: {}", employeeServiceResponse);
        } catch (Exception exception){
            employeeServiceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            logger.error("user not created : {}",employeeServiceResponse);
        }
        return employeeServiceResponse;
    }

    @GetMapping
    public ServiceResponse<List<EmployeeResponseDTO>> getAllEmployees() {
        ServiceResponse<List<EmployeeResponseDTO>> employeeServiceResponse = new ServiceResponse<>();
        List<EmployeeResponseDTO> employeeResponseDTOS = null;
        try {
            employeeResponseDTOS = employeeService.viewAllOnboardEmployees();
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(employeeResponseDTOS);
            logger.info("list of created users: {}", employeeServiceResponse);
        } catch (Exception exception) {
            logger.error("users not found : {}",employeeServiceResponse);
            employeeServiceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ServiceResponse<>(HttpStatus.OK, employeeResponseDTOS);
    }

    @GetMapping("/search/path/{employeeId}")
    public ServiceResponse<EmployeeResponseDTO> getEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try{
            EmployeeResponseDTO responseDTO = employeeService.findEmployeeByID(employeeId);
            employeeServiceResponse.setHttpStatus(HttpStatus.FOUND);
            employeeServiceResponse.setResponsePayload(responseDTO);
            logger.info("user found: {}",employeeServiceResponse);
            return employeeServiceResponse;
        } catch (Exception exception) {
            logger.error("user not found : {}",employeeId);
            throw new ResourceNotFoundException("Employee","employeeID",employeeId);
        }
    }

    @GetMapping("/search/request")
    public ServiceResponse<EmployeeResponseDTO> getEmployeeUsingRequestParam(@RequestParam(required = false) Integer employeeId) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try{
            EmployeeResponseDTO responseDTO = employeeService.findEmployeeByID(employeeId);
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(responseDTO);
            logger.info("user found: {}",employeeServiceResponse);
            return employeeServiceResponse;
        } catch (Exception exception) {
            logger.error("user not found : {}",employeeId);
           throw new ResourceNotFoundException("Employee","employeeID",employeeId);
        }
    }

    @DeleteMapping("{employeeId}")
    public ServiceResponse<String> deleteEmployeeData(@PathVariable(value = "employeeId") Integer employeeId) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try {
            EmployeeResponseDTO employeeResponseDTO = employeeService.findEmployeeByID(employeeId);
            employeeServiceResponse.setHttpStatus(HttpStatus.FOUND);
            employeeService.deleteEmployee(employeeId);
            logger.info("user deleted successfully: {}",employeeServiceResponse);
            return new ServiceResponse<>(HttpStatus.ACCEPTED," EmployeeId => "+ employeeId + employeeResponseDTO +" record deleted successfully");
        } catch (Exception exception) {
           // employeeServiceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            logger.error("this is a invalid id to delete : {}",employeeId);
            return new ServiceResponse<>(HttpStatus.NO_CONTENT," EmployeeId => "+ employeeId +" no record found with this Employee Id");
        }
        //return new ServiceResponse<>(HttpStatus.NO_CONTENT," EmployeeId => "+ employeeId +" no record found with this Employee Id");
    }

    @PutMapping("{employeeId}")
    public ServiceResponse<EmployeeResponseDTO> updateEmployeeById(@PathVariable(value = "employeeId") Integer employeeId, @RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try {
            EmployeeResponseDTO employeeResponseDTO = employeeService.updateEmployeeByEmployeeId(employeeId,employeeRequestDTO);
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(employeeResponseDTO);
            logger.info("user update successfully: {}",employeeServiceResponse);
            return employeeServiceResponse;
        } catch (Exception exception) {
            employeeServiceResponse.setHttpStatus(HttpStatus.NOT_MODIFIED);
            logger.error("this is a invalid id to update : {}",employeeId);
            throw new ResourceNotFoundException("Employee","employeeID",employeeId);
        }
    }
}
