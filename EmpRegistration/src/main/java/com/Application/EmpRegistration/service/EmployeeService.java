package com.Application.EmpRegistration.service;

import java.util.List;

import com.Application.EmpRegistration.entity.Employee;
import com.Application.EmpRegistration.payload.EmployeeDto;
import com.Application.EmpRegistration.payload.EmployeeResponse;

public interface EmployeeService {

	EmployeeDto createEmployee(EmployeeDto employeeDto);

	List<Employee> getAllEmployees();

	EmployeeDto getEmployeeById(long id);

	EmployeeDto updateEmployee(EmployeeDto employeeDto, long id);

	void deleteEmployeeById(long id);

	List<Employee> getEmployeeByFirstName(String firstName);

	EmployeeResponse getAllEmployeesSorted(int pageNo, int pageSize, String order, String sortDir);

}
