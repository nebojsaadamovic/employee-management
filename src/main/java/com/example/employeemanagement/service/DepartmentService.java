package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Long id, Department departmentDetails);

    List<Object[]> listDepartByAvgSalaryAndNumEmployees();

    List<Employee> getEmployeesByDepartmentId(Long departmentId);

}
