package com.Application.EmpRegistration.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
	private List<EmployeeDto> firstName;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
}
