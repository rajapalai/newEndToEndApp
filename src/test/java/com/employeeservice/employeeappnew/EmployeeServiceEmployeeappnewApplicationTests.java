package com.employeeservice.employeeappnew;

import com.employeeservice.employeeappnew.controller.EmployeeController;
import com.employeeservice.employeeappnew.dao.EmployeeDao;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import com.employeeservice.employeeappnew.service.EmployeeService;
import com.employeeservice.employeeappnew.util.EmployeeAppUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeServiceEmployeeappnewApplicationTests {

    @Autowired
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeAppUtil employeeAppUtil;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeDao employeeDao;

    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(EmployeeController.class).build();
    }

    @Test
    public void addEmployeeOnboardDataTest() throws Exception {
//        ServiceResponse<EmployeeResponseDTO> employeeServiceResponse = new ServiceResponse<>();
       // EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(10,"Smaranika","Pattanyak",50000,"liza@gmail.com","9854789632","DEVOPS");
//        employeeServiceResponse.setResponsePayload(employeeResponseDTO);
//        employeeServiceResponse.setHttpStatus(HttpStatus.CREATED);
        Mockito.when(employeeDao.save(ArgumentMatchers.any(EmployeeEntity.class))).thenReturn(EmployeeBuilder.getEntity());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employee-service")
                .content(convertObjectAsString(EmployeeBuilder.getResponse()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists());
        Mockito.verify(employeeDao, Mockito.times(1)).save(ArgumentMatchers.any(EmployeeEntity.class));
        Mockito.verifyNoMoreInteractions(employeeDao);
    }

    @Test
    public void getById() throws Exception {
        //EmployeeResponseDTO employeeResponseDTO1 =  new EmployeeResponseDTO(10,"Smaranika","Pattanyak",50000,"liza@gmail.com","9854789632","DEVOPS");
        Mockito.when(employeeDao.findById(10)).thenReturn(Optional.of(EmployeeBuilder.getEntity()));
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee-service/search/path/"+10)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists());
        Mockito.verify(employeeDao, Mockito.times(1)).findById(10);
        Mockito.verifyNoMoreInteractions(employeeDao);
    }

    private String convertObjectAsString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
