package com.employeeservice.employeeappnew.security.securityConfig;

import com.employeeservice.employeeappnew.exception.UsernameOrEmailNotFoundException;
import com.employeeservice.employeeappnew.security.securityDao.SecurityUserDao;
import com.employeeservice.employeeappnew.security.securityEntity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServices implements UserDetailsService {

    private SecurityUserDao securityUserDao;

    public CustomUserDetailsServices(SecurityUserDao securityUserDao) {
        this.securityUserDao = securityUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = securityUserDao.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameOrEmailNotFoundException("User not found with username or email : " + usernameOrEmail));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}
