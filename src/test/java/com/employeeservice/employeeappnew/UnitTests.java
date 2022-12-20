package com.employeeservice.employeeappnew;

import com.employeeservice.employeeappnew.controller.EmployeeController;
import com.employeeservice.employeeappnew.dao.EmployeeDao;
import com.employeeservice.employeeappnew.dto.APIResponse;
import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.service.EmployeeService;
import com.employeeservice.employeeappnew.util.ServiceResponse;
import org.json.JSONException;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.employeeservice.employeeappnew.controller.EmployeeController.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTests {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private List<EmployeeResponseDTO> employeeResponseDTOList;

    private EmployeeRequestDTO employeeRequestDTO;

    private EmployeeResponseDTO employeeResponseDTO;


    @Test
    @Order(1)
    public void test_viewAllOnboardEmployees() throws JSONException {
        employeeResponseDTOList = new ArrayList<EmployeeResponseDTO>();
        employeeResponseDTOList.add(new EmployeeResponseDTO(1,"Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS"));
        employeeResponseDTOList.add(new EmployeeResponseDTO(2,"Amarjeet","Palai",60000,"amar@gmail.com","7854968753","DEVOPS"));
        employeeResponseDTOList.add(new EmployeeResponseDTO(3,"Liza","Sen",40000,"liza@gmail.com","2678945321","DEVOPS"));

        when(employeeService.viewAllOnboardEmployees()).thenReturn(employeeResponseDTOList);
        ServiceResponse<List<EmployeeResponseDTO>> employeeServiceResponse = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK,employeeServiceResponse.getHttpStatus());
        assertEquals(3,employeeServiceResponse.getResponsePayload().size());
    }

    @Test
    @Order(2)
    public void test_onboardNewEmployee() throws JSONException {
        employeeResponseDTO = new EmployeeResponseDTO(1,"Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS");
        employeeRequestDTO = new EmployeeRequestDTO("Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS");

        when(employeeService.onboardNewEmployee(employeeRequestDTO)).thenReturn(employeeResponseDTO);
        ResponseEntity<APIResponse> employeeServiceResponse = employeeController.addEmployeeOnboardData(employeeRequestDTO);

        assertEquals(HttpStatus.CREATED,employeeServiceResponse.getStatusCode());
//        assertEquals());
    }

    @Test
    @Order(3)
    public void test_findEmployeeByID() throws JSONException {
        employeeResponseDTO = new EmployeeResponseDTO(1,"Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS");
        Integer Id = 1;

        when(employeeService.findEmployeeByID(Id)).thenReturn(employeeResponseDTO);
        ResponseEntity<APIResponse> employeeServiceResponse = (ResponseEntity<APIResponse>) employeeController.getEmployeeById(Id);

        assertEquals(HttpStatus.OK,employeeServiceResponse.getStatusCode());
        assertEquals(1,Id);

    }

    @Test
    @Order(4)
    public void test_updateEmployeeByEmployeeId() throws JSONException {
        employeeResponseDTO = new EmployeeResponseDTO(1,"Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS");
        Integer Id = 1;
        employeeRequestDTO = new EmployeeRequestDTO("Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS");

        when(employeeService.findEmployeeByID(Id)).thenReturn(employeeResponseDTO);
        when(employeeService.updateEmployeeByEmployeeId(Id,employeeRequestDTO)).thenReturn(employeeResponseDTO);

        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = employeeController.updateEmployeeById(Id,employeeRequestDTO);
        assertEquals(HttpStatus.OK,employeeServiceResponse.getHttpStatus());
        assertEquals(1,employeeServiceResponse.getResponsePayload().getId());
        assertEquals("Smaranika",employeeServiceResponse.getResponsePayload().getEmployeeFirstName());
        assertEquals("Pattanayak",employeeServiceResponse.getResponsePayload().getEmployeeLastName());
        assertEquals(50000,employeeServiceResponse.getResponsePayload().getEmployeeSalary());
        assertEquals("smaranika@gmail.com",employeeServiceResponse.getResponsePayload().getEmail());
        assertEquals("5789654789",employeeServiceResponse.getResponsePayload().getContactNumber());
        assertEquals("DEVOPS",employeeServiceResponse.getResponsePayload().getDesignation());
    }

    @Test
    @Order(5)
    public void test_deleteEmployee() throws JSONException {
        employeeResponseDTO = new EmployeeResponseDTO(1,"Smaranika","Pattanayak",50000,"smaranika@gmail.com","5789654789","DEVOPS");
        Integer employeeId = 1;

        when(employeeService.findEmployeeByID(employeeId)).thenReturn(employeeResponseDTO);
        ResponseEntity<?> employeeServiceResponse = employeeController.deleteEmployeeData(employeeId);

        assertEquals(HttpStatus.OK,employeeServiceResponse.getStatusCode());

    }
}
