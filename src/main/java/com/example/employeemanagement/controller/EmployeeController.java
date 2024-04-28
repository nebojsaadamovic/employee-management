package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;





    // Get all employees  //radi
    @GetMapping("/list")
     @PreAuthorize("hasAuthority('HR')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get employee by ID  //radi
    @GetMapping("/get-by/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Create new employee  //radi
    @PostMapping("/create")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) throws JsonProcessingException {
        String createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // Update existing employee
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete employee
    @Transactional
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Samo korisnici sa ulogom ADMIN mogu brisati zaposlene
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeAndUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




    @GetMapping("/search")
    public Page<Employee> searchEmployees(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeService.searchEmployees(keyword, pageable);
    }

}
