package com.employeeservice.employeeappnew.security.securityServices;

import com.employeeservice.employeeappnew.exception.EmployeeServiceBusinessException;
import com.employeeservice.employeeappnew.exception.RegisteredUserNotFoundException;
//import com.employeeservice.employeeappnew.security.JWT.JwtTokenProvider;
import com.employeeservice.employeeappnew.security.JWT.JwtTokenProvider;
//import com.employeeservice.employeeappnew.security.JWT_NEW.JwtUtil;
import com.employeeservice.employeeappnew.security.securityDao.SecurityRoleDao;
import com.employeeservice.employeeappnew.security.securityDao.SecurityUserDao;
import com.employeeservice.employeeappnew.security.securityEntity.LoginDTO;
import com.employeeservice.employeeappnew.security.securityEntity.RegisterDto;
import com.employeeservice.employeeappnew.security.securityEntity.Role;
import com.employeeservice.employeeappnew.security.securityEntity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService implements AuthServiceInterface {

    private String userAccess = "ROLE_USER";
    private String adminAccess = "ROLE_ADMIN";
    private AuthenticationManager authenticationManager;
    private SecurityRoleDao securityRoleDao;
    private SecurityUserDao securityUserDao;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
//    private JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       SecurityRoleDao securityRoleDao,
                       SecurityUserDao securityUserDao,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider
//                       JwtUtil jwtUtil
                       ) {

        this.authenticationManager = authenticationManager;
        this.securityRoleDao = securityRoleDao;
        this.securityUserDao = securityUserDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
//        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUserNameOrEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Generate JWT token
            String token = jwtTokenProvider.generateToken(authentication);
            return token;
//            return "LogIn Successfully.. \n" + "Hello " + loginDTO.getUserNameOrEmail() + "\nYou can access this Employee application !.. \nWith this credential !..";
        } catch (Exception exception) {
            throw new RegisteredUserNotFoundException(HttpStatus.BAD_REQUEST, "Please enter a valid username or password!.. ");
        }
    }

    @Override
    public User register(RegisterDto registerDto) {

        //Check username exists in database
        if (securityUserDao.existsByUserName(registerDto.getUserName())) {
            throw new RegisteredUserNotFoundException(HttpStatus.BAD_REQUEST, "Username is already exists!.. ");
        }
        if (securityUserDao.existsByEmail(registerDto.getEmail())) {
            throw new RegisteredUserNotFoundException(HttpStatus.BAD_REQUEST, "Email is already exists!.. ");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(registerDto.getConfirmPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = securityRoleDao.findByName(userAccess).get();
        roles.add(userRole);
        user.setRoles(roles);

        securityUserDao.save(user);

        return user;
    }

    @Override
    public User adminRegister(RegisterDto registerDto) {

        //Check admin exists in database
        if (securityUserDao.existsByUserName(registerDto.getUserName())) {
            throw new RegisteredUserNotFoundException(HttpStatus.BAD_REQUEST, "Username is already exists!.. ");
        }
        if (securityUserDao.existsByEmail(registerDto.getEmail())) {
            throw new RegisteredUserNotFoundException(HttpStatus.BAD_REQUEST, "Email is already exists!.. ");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(registerDto.getConfirmPassword()));


        Set<Role> roles = new HashSet<>();
        Role userRole = securityRoleDao.findByName(adminAccess).get();
        roles.add(userRole);
        user.setRoles(roles);

        securityUserDao.save(user);

        return user;
    }

    @Override
    public List<User> getAllUserDetails() {
        return securityUserDao.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        User getUserById = null;
        try {
            getUserById = securityUserDao.findById(userId)
                    .orElseThrow(() -> new RegisteredUserNotFoundException(HttpStatus.NOT_FOUND, "User not found with this id : " + userId));
            return getUserById;
        } catch (Exception exception) {
            throw new EmployeeServiceBusinessException("Get user by id method failed" + exception.getMessage());
        }
    }
}
