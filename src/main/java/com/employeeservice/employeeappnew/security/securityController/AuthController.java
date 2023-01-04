package com.employeeservice.employeeappnew.security.securityController;

import com.employeeservice.employeeappnew.dto.APIResponse;
import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.security.securityEntity.*;
import com.employeeservice.employeeappnew.security.securityServices.AuthService;
import com.employeeservice.employeeappnew.security.securityServices.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee-service/auth")
public class AuthController {

    public static final String SUCCESS = "Success";
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
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody @Valid LoginDTO loginDTO) {

        String response = authService.login(loginDTO);

//        String token = authService.login(loginDTO);
        JwtAuthResponseDto jwtAuthResponseDto = new JwtAuthResponseDto();
        jwtAuthResponseDto.setAccess_token(response);
        jwtAuthResponseDto.setStatus(HttpStatus.OK);
//        jwtAuthResponseDto.setUserNameOrEmail(response);
//        jwtAuthResponseDto.setPassword(response);
//        jwtAuthResponseDto.setAccess_token(token);
//        jwtAuthResponseDto.setRefresh_token(response);
        return ResponseEntity.ok(jwtAuthResponseDto);
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

    @GetMapping(value = {"/registerdUsers", "/signupUsers"})
    public ResponseEntity<List<User>> getAllListOfUsersData() {
        List<User> getAllUsers = authService.getAllUserDetails();
        return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByID(@PathVariable(value = "userId") Long userId) {
        try {
            User getUserById = authService.getUserById(userId);
            return new ResponseEntity<>(getUserById,HttpStatus.OK);
        }catch (Exception exception){
            throw new ResourceNotFoundException("Id : " + userId , HttpStatus.BAD_REQUEST);
        }
    }
}
