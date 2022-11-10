package com.employeeservice.employeeappnew.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class EmployeeDesignationTypeValidator implements ConstraintValidator<EmployeeDesignationTypeValidation, String> {

    @Override
    public boolean isValid(String designation, ConstraintValidatorContext context){
        List list = Arrays.asList("DEVOPS","DEVELOPER","TESTER","CONSULTANT","ASSOCIATE-ENGINEER","SR.CONSULTANT");
        return list.contains(designation);
    }
}
