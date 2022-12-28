package com.employeeservice.employeeappnew.security.securityServices;

import com.employeeservice.employeeappnew.security.securityEntity.LoginDTO;
import com.employeeservice.employeeappnew.security.securityEntity.RegisterDto;
import com.employeeservice.employeeappnew.security.securityEntity.User;

public interface AuthServiceInterface {
    public String login(LoginDTO loginDTO);

    public User register(RegisterDto registerDto);

    public User adminRegister(RegisterDto registerDto);
}
