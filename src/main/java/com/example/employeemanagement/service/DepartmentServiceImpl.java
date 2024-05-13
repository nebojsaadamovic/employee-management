package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        department.setName(departmentDetails.getName());

        return departmentRepository.save(department);
    }

    @Override
    public List<Object[]> listDepartByAvgSalaryAndNumEmployees() {
        return departmentRepository.getDepartmentStatistics();
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
            return departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department not found"))
                    .getEmployees();
    }


}
