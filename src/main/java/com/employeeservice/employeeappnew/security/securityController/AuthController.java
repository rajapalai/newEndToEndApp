package com.employeeservice.employeeappnew.security.securityController;

import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.security.securityEntity.LoginDTO;
import com.employeeservice.employeeappnew.security.securityEntity.RegisterDto;
import com.employeeservice.employeeappnew.security.securityEntity.Role;
import com.employeeservice.employeeappnew.security.securityEntity.User;
import com.employeeservice.employeeappnew.security.securityServices.AuthService;
import com.employeeservice.employeeappnew.security.securityServices.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee-service")
public class AuthController {

    private AuthService authService;
    private RoleService roleService;

    public AuthController(AuthService authService, RoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
    }

    //BUILD ROLE REST API
    @PostMapping(value = {"/createRole"})
    public ResponseEntity<Role> createNewRole(@RequestBody @Valid Role role) {
        Role responseRole = null;
        try {
            responseRole = roleService.createNewRole(role);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Role name already registered", HttpStatus.BAD_REQUEST, role);
        }
        return new ResponseEntity<>(responseRole, HttpStatus.CREATED);
    }

    //BUILD LOGIN REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        String response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    //BUILD REGISTERED USER REST API
    @PostMapping(value = {"/register/user", "/signup/user"})
    public ResponseEntity<User> userRegister(@RequestBody @Valid RegisterDto registerDto) {
        User user = authService.register(registerDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //BUILD REGISTERED ADMIN REST API
    @PostMapping(value = {"/register/admin", "/signup/admin"})
    public ResponseEntity<User> adminRegister(@RequestBody @Valid RegisterDto registerDto) {
        User user = authService.adminRegister(registerDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}