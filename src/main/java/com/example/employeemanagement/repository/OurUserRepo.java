package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User getUsersByUsername(String username);

    @Query(value = "SELECT * FROM users u WHERE u.id = (SELECT user_id FROM employees WHERE id = :employeeId)", nativeQuery = true)
    User findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);



    @Transactional
    @Modifying
    @Query(value = "UPDATE users AS u " +
            "INNER JOIN employees AS e ON e.user_id = u.id " +
            "SET u.active = FALSE " +
            "WHERE e.id = :employeeId", nativeQuery = true)
    void softDeleteUserByEmployee(Long employeeId);


    User getUserByUsernameAndPassword(String username, String password);

}
