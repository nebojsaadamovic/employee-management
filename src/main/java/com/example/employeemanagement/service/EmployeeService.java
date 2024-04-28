package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.UserDTO;
import com.example.employeemanagement.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    String createEmployee(Employee employee) throws JsonProcessingException;

    String createEmployeDTO(UserDTO userDTO) throws JsonProcessingException;

    Employee updateEmployee(Long id, Employee employeeDetails);

    void deleteEmployee(Long id);

    void deleteEmployeeAndUser(Long id);

   // Page<Employee> searchEmployees(String keyword, int page, int size);
   Page<Employee> searchEmployees(String keyword, Pageable pageable);
}
