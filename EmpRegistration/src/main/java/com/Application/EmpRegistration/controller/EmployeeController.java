package com.Application.EmpRegistration.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Application.EmpRegistration.entity.Employee;
import com.Application.EmpRegistration.payload.EmployeeDto;
import com.Application.EmpRegistration.payload.EmployeeResponse;
import com.Application.EmpRegistration.service.EmployeeService;
import com.Application.EmpRegistration.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createEmployee")
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
	}

	@GetMapping("/allemployees")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/sort")
	public EmployeeResponse getAllEmployeesSorted(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String order) {
		return employeeService.getAllEmployeesSorted(pageNo, pageSize, sortBy, order);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<EmployeeDto> getEmloyeeById(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	@GetMapping("/search/{name}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<Employee>> getEmloyeeByFirstName(@PathVariable(name = "name") String name) {
		return ResponseEntity.ok(employeeService.getEmployeeByFirstName(name));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto,
			@PathVariable(name = "id") long id) {

		EmployeeDto employeeResponse = employeeService.updateEmployee(employeeDto, id);

		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable(name = "id") long id) {

		employeeService.deleteEmployeeById(id);

		return new ResponseEntity<>("Deleted employee id - " + id, HttpStatus.OK);
	}
}
