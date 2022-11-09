package com.employeeservice.employeeappnew.controller;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.service.EmployeeService;
import com.employeeservice.employeeappnew.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee-service")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ServiceResponse<EmployeeResponseDTO> addEmployeeOnboardData(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try{
            EmployeeResponseDTO newEmployee = employeeService.onboardNewEmployee(employeeRequestDTO);
            employeeServiceResponse.setHttpStatus(HttpStatus.CREATED);
            employeeServiceResponse.setResponsePayload(newEmployee);
        } catch (Exception exception){
            employeeServiceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return employeeServiceResponse;
    }

    @GetMapping
    public ServiceResponse<List<EmployeeResponseDTO>> getAllEmployees() {
        ServiceResponse<List<EmployeeResponseDTO>> employeeServiceResponse = new ServiceResponse<>();
        try {
            List<EmployeeResponseDTO> employeeResponseDTOS = employeeService.viewAllOnboardEmployees();
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(employeeResponseDTOS);
        } catch (Exception exception) {
            employeeServiceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return employeeServiceResponse;
    }

    @GetMapping("/search/path/{employeeId}")
    public ServiceResponse<EmployeeResponseDTO> getEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try{
            EmployeeResponseDTO responseDTO = employeeService.findEmployeeByID(employeeId);
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(responseDTO);
        } catch (Exception exception) {
            employeeServiceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return employeeServiceResponse;
    }

    @GetMapping("/search/request")
    public ServiceResponse<EmployeeResponseDTO> getEmployeeUsingRequestParam(@RequestParam(required = false) Integer employeeId) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try{
            EmployeeResponseDTO responseDTO = employeeService.findEmployeeByID(employeeId);
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(responseDTO);
        } catch (Exception exception) {
            employeeServiceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return employeeServiceResponse;
    }

    @DeleteMapping("{employeeId}")
    public ServiceResponse<String> deleteEmployeeData(@PathVariable(value = "employeeId") Integer employeeId) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try {
            EmployeeResponseDTO employeeResponseDTO = employeeService.findEmployeeByID(employeeId);
            employeeServiceResponse.setHttpStatus(HttpStatus.FOUND);
            employeeService.deleteEmployee(employeeId);
            return new ServiceResponse<>(HttpStatus.NO_CONTENT," EmployeeId => "+ employeeId + employeeResponseDTO +" record deleted successfully");
        } catch (Exception exception) {
            employeeServiceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ServiceResponse<>(HttpStatus.NO_CONTENT," EmployeeId => "+ employeeId +" record deleted successfully");
    }

    @PutMapping("{employeeId}")
    public ServiceResponse<EmployeeResponseDTO> updateEmployeeById(@PathVariable(value = "employeeId") Integer employeeId, @RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
        try {
            EmployeeResponseDTO employeeResponseDTO = employeeService.updateEmployeeByEmployeeId(employeeId,employeeRequestDTO);
            employeeServiceResponse.setHttpStatus(HttpStatus.OK);
            employeeServiceResponse.setResponsePayload(employeeResponseDTO);
        } catch (Exception exception) {
            employeeServiceResponse.setHttpStatus(HttpStatus.NOT_MODIFIED);
        }
        return employeeServiceResponse;
    }
}
