package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {



    @Query("SELECT e FROM Employee e WHERE (e.active = true) AND ((LOWER(e.firstName) LIKE %:firstName%) OR (LOWER(e.lastName) LIKE %:lastName%))")
    Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndActive(@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable pageable);


    Employee getEmployeeByFirstNameAndLastName(String firstName, String lastName);


    @Query("SELECT e FROM Employee e JOIN e.user u WHERE u.email = :email")
    Employee findEmployeeByEmail(@Param("email") String email);


    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.active = false WHERE e.id = :id")
    void softDeleteEmployee(Long id);

    @Query("SELECT e FROM Employee e WHERE e.active = true")
    List<Employee> findAllActiveEmployees();

}
