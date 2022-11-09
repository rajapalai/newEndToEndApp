package com.employeeservice.employeeappnew.util;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;

public class EmployeeAppUtil {

	// EmployeeDTO -> EmployeeEntity

	public static EmployeeEntity mapEmployeeDTOToEmployeeEntity(EmployeeRequestDTO employeeRequestDTO) {
	    EmployeeEntity employeeEntity = new EmployeeEntity();
	    employeeEntity.setEmployeeFirstName(employeeRequestDTO.getEmployeeFirstName());
	    employeeEntity.setEmployeeLastName(employeeRequestDTO.getEmployeeLastName());
	    employeeEntity.setEmployeeJoiningDate(employeeRequestDTO.getEmployeeJoiningDate());
	    employeeEntity.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());
		employeeEntity.setEmployeeEmail(employeeEntity.getEmployeeEmail());
		employeeEntity.setEmployeeContactNumber(employeeEntity.getEmployeeContactNumber());
		employeeEntity.setEmployeeDesignation(employeeEntity.getEmployeeDesignation());
	    return employeeEntity;
	}

	//EmployeeEntity -> EmployeeDTO

	public static EmployeeResponseDTO mapEmployeeEntityToEmployeeDTO(EmployeeEntity employeeEntity){
	    EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
	    employeeResponseDTO.setEmployeeId(employeeEntity.getEmployeeId());
	    employeeResponseDTO.setEmployeeFirstName(employeeEntity.getEmployeeFirstName());
	    employeeResponseDTO.setEmployeeLastName(employeeEntity.getEmployeeLastName());
	    employeeResponseDTO.setEmployeeJoiningDate(employeeEntity.getEmployeeJoiningDate());
	    employeeResponseDTO.setEmployeeSalary(employeeEntity.getEmployeeSalary());
		employeeResponseDTO.setEmployeeEmail(employeeEntity.getEmployeeEmail());
		employeeResponseDTO.setEmployeeContactNumber(employeeEntity.getEmployeeContactNumber());
		employeeResponseDTO.setEmployeeDesignation(employeeEntity.getEmployeeDesignation());
	    return employeeResponseDTO;
	}
}
