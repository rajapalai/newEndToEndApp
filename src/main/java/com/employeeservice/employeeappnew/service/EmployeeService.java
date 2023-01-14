package com.employeeservice.employeeappnew.service;

import com.employeeservice.employeeappnew.dao.EmployeeDao;
import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import com.employeeservice.employeeappnew.exception.EmployeeServiceBusinessException;
import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.util.EmployeeAppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@Slf4j
@CacheConfig(cacheNames = "employeeRequestDTO")
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @CachePut(key = "#employeeRequestDTO.email")
    public EmployeeResponseDTO onboardNewEmployee(EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO;

        try {
            log.info("EmployeeService:onboardNewEmployee execution started.");
            EmployeeEntity employeeEntity = EmployeeAppUtil.mapEmployeeDTOToEmployeeEntity(employeeRequestDTO);
            log.debug("EmployeeService:onboardNewEmployee request parameters {}", EmployeeAppUtil.jsonAsString(employeeRequestDTO));

            EmployeeEntity addEmployee = employeeDao.save(employeeEntity);
            employeeResponseDTO = EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(addEmployee);
            log.debug("EmployeeService:onboardNewEmployee received response from Database {}", EmployeeAppUtil.jsonAsString(employeeRequestDTO));

        } catch (Exception e) {
            log.error("Exception occurred while persisting employee to database , Exception message {}", e.getMessage());
            throw new EmployeeServiceBusinessException("Exception occurred while create a new employee");
        }
        log.info("EmployeeService:onboardNewEmployee execution ended.");
        return employeeResponseDTO;

//		//Convert DTO -> Entity
//		EmployeeEntity employeeEntity = EmployeeAppUtil.mapEmployeeDTOToEmployeeEntity(employeeRequestDTO);
////		EmployeeEntity addEmployee = null;
//		try {
//			EmployeeEntity addEmployee = employeeDao.save(employeeEntity);
//			EmployeeResponseDTO employeeResponseDTO = EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(addEmployee);
//			//employeeResponseDTO.setEmployeeUniqueCode(UUID.randomUUID().toString().split("-")[0]);
//				return employeeResponseDTO;
//		} catch (Exception exception) {
//			throw new EmployeeServiceBusinessException("onboardNewEmployee service method failed....");
//		}
    }

    @Override
    @Cacheable
    public List<EmployeeResponseDTO> viewAllOnboardEmployees() {

        Iterable<EmployeeEntity> employeeEntities = null;
        try {
            log.info("EmployeeService:viewAllOnboardEmployees execution started.");
            employeeEntities = employeeDao.findAll();
            log.debug("EmployeeService:onboardNewEmployee request parameters {}", EmployeeAppUtil.jsonAsString(employeeEntities));
            log.info("EmployeeService:viewAllOnboardEmployees execution ended.");
            return StreamSupport.stream(employeeEntities.spliterator(), false)
                    .map(EmployeeAppUtil::mapEmployeeEntityToEmployeeDTO)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            log.error("Exception occurred while persisting employee to database , Exception message {}", exception.getMessage());
            throw new EmployeeServiceBusinessException("viewAllOnboardEmployees service method failed....");
        }

    }

    @Override
    @Cacheable(key = "#employeeId")
    public EmployeeResponseDTO findEmployeeByID(Integer employeeId) {

        EmployeeEntity employeeEntity = null;
        try {
            employeeEntity = employeeDao.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeID", employeeId));
            return EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(employeeEntity);
        } catch (Exception exception) {
            throw new EmployeeServiceBusinessException("findEmployeeByID service method failed....");
        }
    }

    @Override
//	@Cacheable(value = "employee")
    public void deleteEmployee(Integer employeeId) {
        try {
            employeeDao.deleteById(employeeId);
        } catch (Exception e) {
            //throw new ResourceNotFoundException("Employee","employeeID",employeeId);
            throw new EmployeeServiceBusinessException("deleteEmployee service method failed....");
        }
    }

    @Override
    @CachePut(cacheNames = "employeeRequestDTO", key = "#employee.employeeId")
    public EmployeeResponseDTO updateEmployeeByEmployeeId(Integer employeeId, EmployeeRequestDTO employeeRequestDTO) {

        //Get the existing object
        EmployeeEntity existingEmployeeEntity = null;
        try {
            existingEmployeeEntity = employeeDao.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeID", employeeId));
            //Modified the existing object with the new value
            existingEmployeeEntity.setEmployeeFirstName(employeeRequestDTO.getEmployeeFirstName());
            existingEmployeeEntity.setEmployeeLastName(employeeRequestDTO.getEmployeeLastName());
            //existingEmployeeEntity.setEmployeeJoiningDate(employeeRequestDTO.getEmployeeJoiningDate());
            existingEmployeeEntity.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());
            existingEmployeeEntity.setEmail(employeeRequestDTO.getEmail());
            existingEmployeeEntity.setContactNumber(employeeRequestDTO.getContactNumber());
            existingEmployeeEntity.setDesignation(employeeRequestDTO.getDesignation());
            //save the modified value
            EmployeeEntity updateEmployeeEntity = employeeDao.save(existingEmployeeEntity);
            return EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(updateEmployeeEntity);
        } catch (Exception exception) {
            throw new EmployeeServiceBusinessException("updateEmployeeByEmployeeId service method failed....");
        }
    }

//    @Cacheable(value = "employee")
    public Map<String, List<EmployeeResponseDTO>> getAllEmployeeDesignationByTypes() {
        try {
            log.info("EmployeeService:getAllEmployeeDesignationByTypes execution started.");

            Map<String, List<EmployeeResponseDTO>> employeeDesignationMap =
                    employeeDao.findAll().stream()
                            .map(EmployeeAppUtil::mapEmployeeEntityToEmployeeDTO)
                            .filter(employeeResponseDTO -> employeeResponseDTO.getDesignation() != null)
                            .collect(Collectors.groupingBy(EmployeeResponseDTO::getDesignation));

            log.info("EmployeeService:getAllEmployeeDesignationByTypes execution ended.");
            return employeeDesignationMap;

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving employee designation grouping by type from database , Exception message {}", ex.getMessage());
            throw new EmployeeServiceBusinessException("Exception occurred while fetch employee designation from Database ");
        }
    }
}
