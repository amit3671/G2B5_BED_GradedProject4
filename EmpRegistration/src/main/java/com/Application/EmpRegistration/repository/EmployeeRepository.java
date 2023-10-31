package com.Application.EmpRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Application.EmpRegistration.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
