package com.employeeservice.employeeappnew.service;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.util.EmployeeAppUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.employeeservice.employeeappnew.dao.EmployeeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class EmployeeService implements EmployeeServiceInterface{

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public EmployeeResponseDTO onboardNewEmployee(EmployeeRequestDTO employeeRequestDTO) {

		//Convert DTO -> Entity
		EmployeeEntity employeeEntity = EmployeeAppUtil.mapEmployeeDTOToEmployeeEntity(employeeRequestDTO);
		EmployeeEntity addEmployee = employeeDao.save(employeeEntity);

		//Convert Entity -> DTO
		EmployeeResponseDTO employeeResponseDTO = EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(addEmployee);
		employeeResponseDTO.setEmployeeUniqueCode(UUID.randomUUID().toString().split("_")[1]);
		return employeeResponseDTO;
	}

	@Override
	public List<EmployeeResponseDTO> viewAllOnboardEmployees() {
		Iterable<EmployeeEntity> employeeEntities = employeeDao.findAll();
		return StreamSupport.stream(employeeEntities.spliterator(),false)
				.map(EmployeeAppUtil::mapEmployeeEntityToEmployeeDTO)
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeResponseDTO findEmployeeByID(Integer employeeId) {
		EmployeeEntity employeeEntity = employeeDao.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee","employeeID",employeeId));
		return EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(employeeEntity);
	}

	@Override
	public void deleteEmployee(Integer employeeId) {
		try{
			employeeDao.deleteById(employeeId);
		} catch (Exception e){
			throw new ResourceNotFoundException("Employee","employeeID",employeeId);
		}
	}

	@Override
	public EmployeeResponseDTO updateEmployeeByEmployeeId(Integer employeeId, EmployeeRequestDTO employeeRequestDTO) {

		//Get the existing object
		EmployeeEntity existingEmployeeEntity = employeeDao.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee","employeeID",employeeId));

		//Modified the existing object with the new value
		existingEmployeeEntity.setEmployeeFirstName(employeeRequestDTO.getEmployeeFirstName());
		existingEmployeeEntity.setEmployeeLastName(employeeRequestDTO.getEmployeeLastName());
		existingEmployeeEntity.setEmployeeJoiningDate(employeeRequestDTO.getEmployeeJoiningDate());
		existingEmployeeEntity.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());
		existingEmployeeEntity.setEmployeeEmail(employeeRequestDTO.getEmployeeEmail());
		existingEmployeeEntity.setEmployeeContactNumber(employeeRequestDTO.getEmployeeContactNumber());
		existingEmployeeEntity.setEmployeeDesignation(employeeRequestDTO.getEmployeeDesignation());
		//save the modified value
		EmployeeEntity updateEmployeeEntity = employeeDao.save(existingEmployeeEntity);
		return EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(updateEmployeeEntity);
	}
}
