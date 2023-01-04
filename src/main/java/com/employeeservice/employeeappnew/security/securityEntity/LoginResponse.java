package com.employeeservice.employeeappnew.security.securityEntity;

import com.employeeservice.employeeappnew.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse<T> {

    private HttpStatus status;
//    private List<ErrorDTO> errors;
    private T results;

}
