package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.UserDTO;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.entity.User;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.OurUserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OurUserRepo userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
//
    @Override
    public String createEmployee(Employee employee) throws JsonProcessingException {
//        User user = employee.getUser();
//        Employee existingEmployee = employeeRepository.getEmployeeByFirstNameAndLastName(employee.getFirstName(), employee.getLastName());
//        if (existingEmployee != null) {
//            return objectMapper.writeValueAsString("Employee already exists");
//        }
//        if (user != null) {
//            Optional<User> existingUser = userRepository.findByEmail(user.getUsername());
//            if (existingUser.isEmpty()) {
//                userRepository.save(user);
//            } else {
//                return objectMapper.writeValueAsString("User already exists");
//            }
//        }
//        try {
//            employeeRepository.save(employee);
//            return objectMapper.writeValueAsString("Employee saved successfully");
//        } catch (DataIntegrityViolationException e) {
//            return objectMapper.writeValueAsString("Employee not saved successfully: " + e.getMessage());
//        }
return null;
   }


    @Override
    public String createEmployeDTO(UserDTO userDTO) throws JsonProcessingException {
        Employee existEmployee = employeeRepository.findEmployeeByEmail(userDTO.getEmail());
        if (existEmployee != null) {
            return objectMapper.writeValueAsString("Employee already exists");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());
        System.out.println(user);
        if (user == null) {
            user = new User();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user = userRepository.save(user);
        }else{
            return objectMapper.writeValueAsString("Username already exists");
        }
        Department department = departmentRepository.findById(userDTO.getDepartment().getId()).orElse(null);
        if (department == null) {
            department = new Department();
            department.setId(userDTO.getDepartment().getId());
            department.setName(userDTO.getDepartment().getName());
            department = departmentRepository.save(department);
        }else{
            return objectMapper.writeValueAsString("Department already exists");
        }
        Employee employee = new Employee();
        employee.setFirstName(userDTO.getFirstName());
        employee.setLastName(userDTO.getLastName());
        employee.setSalary(Double.parseDouble(userDTO.getSallary()));
        employee.setDepartment(department);
        employee.setUser(user);
        employeeRepository.save(employee);

        return objectMapper.writeValueAsString("Employee saved");
    }





    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setSalary(employeeDetails.getSalary());

        Department department = employeeDetails.getDepartment();
        if (Objects.nonNull(department)) {
            Optional<Department> existingDepartmentOptional = departmentRepository.findById(department.getId());
            if (existingDepartmentOptional.isPresent()) {
                Department existingDepartment = existingDepartmentOptional.get();
                existingDepartment.setName(department.getName());
                departmentRepository.save(existingDepartment);
            }
        }
        User user = employeeDetails.getUser();
        if (Objects.nonNull(user)) {
            Optional<User> existingUserOptional = userRepository.findById(user.getId());
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
            }
        }
        return employeeRepository.save(employee);
    }


    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }


    public void deleteEmployeeAndUser(Long id) {
        try {
            // Pronalazimo zaposlenika prema ID-u
            Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Zaposlenik ne postoji s ID-om: " + id));
            // Pronalazimo korisnika koji je povezan s tim zaposlenikom
            User user = userRepository.findByEmployeeId(id);
            if (user == null) {
                throw new IllegalArgumentException("Korisnik nije pronađen za zaposlenika s ID-om: " + id);
            }

            // Brišemo zaposlenika
            employeeRepository.delete(employee);

            // Brišemo korisnika
            userRepository.delete(user);
            if (!employeeRepository.existsById(id) && userRepository.findByEmployeeId(id) == null) {
                System.out.println("nesoooo");
                objectMapper.writeValueAsString("Zaposlenik i korisnik su uspješno izbrisani");
                //return "Zaposlenik i korisnik su uspješno izbrisani.";
            } else {
                throw new IllegalStateException("Došlo je do greške prilikom brisanja zaposlenika i korisnika.");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }


    @Override
    public Page<Employee> searchEmployees(String keyword, Pageable pageable) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword, pageable);
    }
}
