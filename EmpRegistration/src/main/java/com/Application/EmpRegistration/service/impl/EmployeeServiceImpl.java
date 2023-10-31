package com.Application.EmpRegistration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Application.EmpRegistration.entity.Employee;
import com.Application.EmpRegistration.exception.ResourceNotFoundException;
import com.Application.EmpRegistration.payload.EmployeeDto;
import com.Application.EmpRegistration.payload.EmployeeResponse;
import com.Application.EmpRegistration.repository.EmployeeRepository;
import com.Application.EmpRegistration.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private ModelMapper mapper;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
		this.employeeRepository = employeeRepository;
		this.mapper = mapper;
	}

	@Override
	public EmployeeResponse getAllEmployeesSorted(int pageNo, int pageSize, String order, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(order).ascending()
				: Sort.by(order).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Employee> employees = employeeRepository.findAll(pageable);

		List<Employee> listOfEmployees = employees.getContent();

		List<EmployeeDto> content = listOfEmployees.stream().map(employee -> mapToDTO(employee))
				.collect(Collectors.toList());

		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setFirstName(content);
		employeeResponse.setPageNo(employees.getNumber());
		employeeResponse.setPageSize(employees.getSize());
		employeeResponse.setTotalElements(employees.getTotalElements());
		employeeResponse.setTotalPages(employees.getTotalPages());
		employeeResponse.setLast(employees.isLast());

		return employeeResponse;
	}

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {

		Employee employee = mapToEntity(employeeDto);
		Employee newEmployee = employeeRepository.save(employee);

		EmployeeDto employeeResponse = mapToDTO(newEmployee);
		return employeeResponse;
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	@Override
	public EmployeeDto getEmployeeById(long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
		return mapToDTO(employee);
	}

	@Override
	public List<Employee> getEmployeeByFirstName(String firstName) {

		Employee matchingemployee = new Employee();
		matchingemployee.setFirstName(firstName);
		ExampleMatcher examplematcher = ExampleMatcher.matching()
				.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().caseSensitive())
				.withIgnorePaths("lastName");
		List<Employee> MatchingNames = new ArrayList<>();
		MatchingNames.add(matchingemployee);
		Example<Employee> example = Example.of(matchingemployee, examplematcher);
		return employeeRepository.findAll(example);
	}

	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setEmail(employeeDto.getEmail());

		Employee updatedEmployee = employeeRepository.save(employee);
		return mapToDTO(updatedEmployee);
	}

	@Override
	public void deleteEmployeeById(long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
		employeeRepository.delete(employee);
	}

	private EmployeeDto mapToDTO(Employee employee) {
		EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
		return employeeDto;
	}

	private Employee mapToEntity(EmployeeDto employeeDto) {
		Employee employee = mapper.map(employeeDto, Employee.class);
		return employee;
	}

}
