package com.employeeservice.employeeappnew.customAnnotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmployeeDesignationTypeValidator.class)
public @interface EmployeeDesignationTypeValidation {

    String message() default "Please input employee designation -> DEVOPS/DEVELOPER/TESTER/CONSULTANT/ASSOCIATE-ENGINEER/SR.CONSULTANT";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
