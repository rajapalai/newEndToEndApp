package com.employeeservice.employeeappnew.security.securityEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponseDto {

    //    private String userNameOrEmail;
//    private String password;
    private HttpStatus status;
    private String token_type = "Bearer";
    private String access_token;

//    private String refresh_token;
}
