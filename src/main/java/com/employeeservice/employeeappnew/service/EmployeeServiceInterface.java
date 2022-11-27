package com.employeeservice.employeeappnew.service;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeServiceInterface {

    public EmployeeResponseDTO onboardNewEmployee(EmployeeRequestDTO employeeRequestDTO);
    public List<EmployeeResponseDTO> viewAllOnboardEmployees();
    public EmployeeResponseDTO findEmployeeByID(Integer employeeId);
    public void deleteEmployee(Integer employeeId);
    public EmployeeResponseDTO updateEmployeeByEmployeeId(Integer employeeId, EmployeeRequestDTO employeeRequestDTO);
}
