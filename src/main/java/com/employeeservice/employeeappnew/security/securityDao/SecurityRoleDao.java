package com.employeeservice.employeeappnew.security.securityDao;

import com.employeeservice.employeeappnew.security.securityEntity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRoleDao extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
