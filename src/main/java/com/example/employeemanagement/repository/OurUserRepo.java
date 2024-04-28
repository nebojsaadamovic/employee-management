package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User getUsersByUsername(String username);

    @Query(value = "SELECT * FROM users u WHERE u.id = (SELECT user_id FROM employees WHERE id = :employeeId)", nativeQuery = true)
    User findByEmployeeId(@Param("employeeId") Long employeeId);

    User findByUsernameAndPassword(String username, String password);


    User getUserByUsernameAndPassword(String username, String password);

}
