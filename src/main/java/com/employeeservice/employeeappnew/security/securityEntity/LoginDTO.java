package com.employeeservice.employeeappnew.security.securityEntity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private String userNameOrEmail;
    private String password;
}
