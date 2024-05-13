package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT d.name AS departmentName, AVG(e.salary) AS avgSalary, COUNT(e.id) AS numEmployees " +
            "FROM departments d LEFT JOIN employees e ON d.id = e.department_id " +
            "GROUP BY d.id, d.name", nativeQuery = true)
    List<Object[]> getDepartmentStatistics();

    Department findByName(String department);
}
