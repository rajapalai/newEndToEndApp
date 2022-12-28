package com.employeeservice.employeeappnew.security.securityEntity;

import com.employeeservice.employeeappnew.customAnnotation.Password;
import com.employeeservice.employeeappnew.customAnnotation.PasswordValueMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Name name should not be NULL or EMPTY")
    private String name;

    @NotBlank(message = "User name should not be NULL or EMPTY")
    private String userName;

    @NotBlank(message = "Email should not be NULL or EMPTY")
    private String email;

    @NotBlank(message = "Please enter password")
    @Password
    private String password;

    @NotBlank(message = "Please enter password")
    @Password
    private String confirmPassword;
}
