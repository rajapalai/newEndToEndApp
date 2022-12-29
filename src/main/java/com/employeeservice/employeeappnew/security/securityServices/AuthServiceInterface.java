package com.employeeservice.employeeappnew.security.securityServices;

import com.employeeservice.employeeappnew.security.securityEntity.LoginDTO;
import com.employeeservice.employeeappnew.security.securityEntity.RegisterDto;
import com.employeeservice.employeeappnew.security.securityEntity.User;

import java.util.List;

public interface AuthServiceInterface {
    public String login(LoginDTO loginDTO);

    public User register(RegisterDto registerDto);

    public User adminRegister(RegisterDto registerDto);

    public List<User> getAllUserDetails();

    public User getUserById(Long userId);
}
