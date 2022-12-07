package com.employeeservice.employeeappnew.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	private String employeeFirstName;

	@Column(unique = true)
	private String employeeLastName;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
//	private Date employeeJoiningDate;

	private double employeeSalary;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String contactNumber;

	private String designation;
}
