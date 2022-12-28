package com.employeeservice.employeeappnew.security.securityServices;

import com.employeeservice.employeeappnew.security.securityDao.SecurityRoleDao;
import com.employeeservice.employeeappnew.security.securityEntity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private SecurityRoleDao securityRoleDao;

    public RoleService(SecurityRoleDao securityRoleDao) {
        this.securityRoleDao = securityRoleDao;
    }

    public Role createNewRole(Role role) {
        return securityRoleDao.save(role);
    }
}
