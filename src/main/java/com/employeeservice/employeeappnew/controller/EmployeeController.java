package com.employeeservice.employeeappnew.controller;

import com.employeeservice.employeeappnew.dto.APIResponse;
import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.service.EmployeeService;
import com.employeeservice.employeeappnew.util.EmployeeAppUtil;
import com.employeeservice.employeeappnew.util.ServiceResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee-service")
@Slf4j
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<APIResponse> addEmployeeOnboardData(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
        APIResponse<EmployeeResponseDTO> employeeServiceResponse = new APIResponse<>();
        try{
            EmployeeResponseDTO newEmployee = employeeService.onboardNewEmployee(employeeRequestDTO);
            employeeServiceResponse.setStatus(SUCCESS);
            employeeServiceResponse.setResults(newEmployee);
            logger.info("EmployeeController::addEmployeeOnboardData request body {}", EmployeeAppUtil.jsonAsString(employeeRequestDTO));
        } catch (Exception exception){
//            employeeServiceResponse.setStatus("Employee data not onboarded : " + HttpStatus.BAD_REQUEST);
//            employeeServiceResponse.setStatus("This employee data already onboarded : " + employeeRequestDTO);
            logger.error("user not created : {}",employeeServiceResponse.getResults());
            throw new ResourceNotFoundException("Employee",employeeRequestDTO,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeServiceResponse, HttpStatus.CREATED);
    }

//    @PostMapping
//    public ResponseEntity<APIResponse> addEmployeeOnboardData(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) {
//
//        log.info("EmployeeController::addEmployeeOnboardData request body {}", EmployeeAppUtil.jsonAsString(employeeRequestDTO));
//
//        EmployeeResponseDTO employeeResponseDTO = employeeService.onboardNewEmployee(employeeRequestDTO);
//        //Builder Design pattern
//
//        APIResponse<EmployeeResponseDTO> responseDTO = APIResponse
//                .<EmployeeResponseDTO>builder()
//                .status(SUCCESS)
//                .results(employeeResponseDTO)
//                .build();
//
//        log.info("EmployeeController::addEmployeeOnboardData response {}", EmployeeAppUtil.jsonAsString(responseDTO));
//
//        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
//    }

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

    //New Api
    //Builder Design Pattern
    @GetMapping("/designationTypes")
    public ResponseEntity<APIResponse> getEmployeesByDesignation() {
        Map<String, List<EmployeeResponseDTO>> employees = employeeService.getAllEmployeeDesignationByTypes();
        APIResponse<Map<String, List<EmployeeResponseDTO>>> responseDTO = APIResponse
                .<Map<String, List<EmployeeResponseDTO>>>builder()
                .status(SUCCESS)
                .results(employees)
                .build();

        log.info("ProductController::getProductsGroupByType by types  {}", EmployeeAppUtil.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/search/path/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        log.info("EmployeeController::getEmployee by id  {} ", employeeId);
        try{
            EmployeeResponseDTO employeeResponseDTO = employeeService.findEmployeeByID(employeeId);

            APIResponse<EmployeeResponseDTO> responseDTO =APIResponse
                    .<EmployeeResponseDTO>builder()
                    .status(SUCCESS)
                    .results(employeeResponseDTO)
                    .build();
            logger.info("EmployeeController::getEmployee by id  {} response {}", employeeId,EmployeeAppUtil
                    .jsonAsString(employeeResponseDTO));
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
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
    public ResponseEntity<?> deleteEmployeeData(@PathVariable(value = "employeeId") Integer employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            logger.info("user deleted successfully: {}",employeeId);
        } catch (Exception exception) {
           // employeeServiceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            logger.error("this is a invalid id to delete : {}",employeeId);
            throw new ResourceNotFoundException("Employee","employeeID",employeeId);
        }
        return new ResponseEntity<>("Employee Deleted successfully",HttpStatus.OK);
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
