package com.Application.EmpRegistration.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmployeeDto {
	private long id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String email;

}
