package com.employeeservice.employeeappnew.dto;

import com.employeeservice.employeeappnew.customAnnotation.EmployeeDesignationTypeValidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeRequestDTO {

	@NotBlank(message = "Employee firstname should not be NULL or EMPTY")
	private String employeeFirstName;

	@NotBlank(message = "Employee lastname should not be NULL or EMPTY")
	private String employeeLastName;

//	@PastOrPresent(message = "Joining date cant not be before date from current")
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
//	private Date employeeJoiningDate;

	@Min(value = 20000, message = "Employee salary can not be less than 20000")
	@Max(value = 300000, message = "Employee salary can not be more than 300000")
	private double employeeSalary;

	@Email(message = "Invalid email Id")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$")
	private String contactNumber;

	@EmployeeDesignationTypeValidation
	private String designation;
}
