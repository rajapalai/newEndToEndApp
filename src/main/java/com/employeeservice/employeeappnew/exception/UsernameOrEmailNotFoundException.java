package com.employeeservice.employeeappnew.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameOrEmailNotFoundException extends AuthenticationException {

    public UsernameOrEmailNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameOrEmailNotFoundException(String msg) {
        super(msg);
    }
}
