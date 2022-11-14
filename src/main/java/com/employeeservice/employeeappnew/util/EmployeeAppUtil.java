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
	   // employeeEntity.setEmployeeJoiningDate(employeeRequestDTO.getEmployeeJoiningDate());
	    employeeEntity.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());
		employeeEntity.setEmail(employeeRequestDTO.getEmail());
		employeeEntity.setContactNumber(employeeRequestDTO.getContactNumber());
		employeeEntity.setDesignation(employeeRequestDTO.getDesignation());
	    return employeeEntity;
	}

	//EmployeeEntity -> EmployeeDTO

	public static EmployeeResponseDTO mapEmployeeEntityToEmployeeDTO(EmployeeEntity employeeEntity){
	    EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
	    employeeResponseDTO.setId(employeeEntity.getId());
	    employeeResponseDTO.setEmployeeFirstName(employeeEntity.getEmployeeFirstName());
	    employeeResponseDTO.setEmployeeLastName(employeeEntity.getEmployeeLastName());
	    //employeeResponseDTO.setEmployeeJoiningDate(employeeEntity.getEmployeeJoiningDate());
	    employeeResponseDTO.setEmployeeSalary(employeeEntity.getEmployeeSalary());
		employeeResponseDTO.setEmail(employeeEntity.getEmail());
		employeeResponseDTO.setContactNumber(employeeEntity.getContactNumber());
		employeeResponseDTO.setDesignation(employeeEntity.getDesignation());
		//employeeResponseDTO.setEmployeeUniqueCode(employeeEntity.getEmployeeUniqueCode());
	    return employeeResponseDTO;
	}
}
